import { useEffect, useState } from "react";
import "../styles/VolunteerGroupList.css"; // Assuming you create a CSS file for styling
import { useTranslation } from "react-i18next";
import { VolunteerGroup } from "@/components/layouts/volunteer/models/VolunteerGroup.tsx";
import { Volunteer } from "@/components/layouts/volunteer/models/Volunteer.tsx";
import axios from "@/api/Axios.tsx";

function VolunteerGroupList() {
    const [volunteerGroups, setVolunteerGroups] = useState<VolunteerGroup[]>([]);
    const [volunteers, setVolunteers] = useState<Volunteer[]>([]);
    const [editingGroupId, setEditingGroupId] = useState<string | null>(null);
    const [groupMembers, setGroupMembers] = useState<Volunteer[]>([]);
    const [removeGroupMembers, setRemoveGroupMembers] = useState<Volunteer[]>([]);

    const [notMembers, setNotGroupMembers] = useState<Volunteer[]>([]);
    const [isPopupVisible, setIsPopupVisible] = useState(false);
    const { t } = useTranslation();

    const [newGroupName, setNewGroupName] = useState("");
    // // Handle form submission to add a new group
    const handleAddGroup = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!newGroupName.trim()) {
            alert(t("volunteer.groupNameRequired")); // Use translation for error message
            return;
        }

        try {
            const response = await axios.post("/volunteerGroups", {groupName: newGroupName});
            setVolunteerGroups((prev) => [...prev, response.data]); // Add the new group to the list
            setNewGroupName(""); // Clear the input field
            alert(t("volunteer.groupCreatedSuccessfully")); // Optional success message
        } catch (error) {
            console.error("Failed to create group:", error);
            alert(t("volunteer.groupCreationFailed")); // Optional error message
        }
    };
    // Fetch volunteer groups
    useEffect(() => {
        axios
            .get("/volunteerGroups")
            .then((response) => setVolunteerGroups(response.data))
            .catch((error) => console.error("Failed to fetch groups:", error));
    }, []);

    console.log(volunteers);
    // Fetch all volunteers
    useEffect(() => {
        axios
            .get("/volunteers")
            .then((response) => setVolunteers(response.data))
            .catch((error) => console.error("Failed to fetch volunteers:", error));
    }, []);

    // Open the editor for a specific group
    const handleEditGroup = async (groupId: string, currentMembers: Volunteer[]) => {
        setEditingGroupId(groupId);
        setGroupMembers(currentMembers); // Set the current members of the group
        await axios
            .get(`/volunteerGroups/${groupId}/notMembers`)
            .then((response) => setNotGroupMembers(response.data))
            .catch((error) => console.error("Failed to fetch groups:", error));
        // setNotGroupMembers(others);
        console.log(notMembers)
        setIsPopupVisible(true);
    };

    // Add a member to the group
    const handleAddMember = (volunteer: Volunteer) => {
        console.log(volunteer.id)
        if (!groupMembers.find((member) => member.id === volunteer.id)) {
            setGroupMembers((prev) => [...prev, volunteer]);
            setNotGroupMembers((prev) => prev.filter((member) => member.id !== volunteer.id));
            setRemoveGroupMembers((prev) => prev.filter((member) => member.id !== volunteer.id))

        }
    };

    // Remove a member from the group
    const handleRemoveMember = (volunteer: Volunteer) => {
        console.log("remove" + volunteer.id)
        if (!removeGroupMembers.find((member) => member.id === volunteer.id)) {
            setRemoveGroupMembers((prev) => [...prev, volunteer]);
            setNotGroupMembers((prev) => [...prev, volunteer]);
            setGroupMembers((prev) => prev.filter((member) => member.id !== volunteer.id));
        }
        console.log(removeGroupMembers)
    };

    // Save the updated group
    const handleSaveGroup = async () => {
        try {
            const updatedGroup = {
                members: groupMembers.map((member) => member.id),
            };
            const removeMembersGroup = {
                members: removeGroupMembers.map((member) => member.id),
            };
            console.log(groupMembers)
            console.log(removeMembersGroup)
            await axios.post(`/volunteerGroups/${editingGroupId}/addMembers`, updatedGroup);

            await axios.post(`/volunteerGroups/${editingGroupId}/removeMembers`, removeMembersGroup);

            axios
                .get("/volunteerGroups")
                .then((response) => setVolunteerGroups(response.data))
                .catch((error) => console.error("Failed to fetch groups:", error));

            setEditingGroupId(null);
            setIsPopupVisible(false);
            setGroupMembers([])
            setRemoveGroupMembers([])
            alert(t("volunteer.groupUpdatedSuccessfully"));
        } catch (error) {
            console.error("Failed to save group:", error);
            alert(t("volunteer.groupUpdateFailed"));
        }
    };

    // Close the popup
    const closePopup = () => {
        setIsPopupVisible(false);
        setEditingGroupId(null);
    };

    return (
        <div className="volunteer-group-list-container">
            <h2>{t("volunteer.volunteerGroupListTitle")}</h2>

            <form onSubmit={handleAddGroup} className="add-group-form">
                <input
                    type="text"
                    value={newGroupName}
                    onChange={(e) => setNewGroupName(e.target.value)}
                    placeholder={t("volunteer.enterGroupName")}
                    className="group-input"
                />
                <button type="submit" className="add-group-button">
                    {t("volunteer.addGroup")}
                </button>
            </form>

            {volunteerGroups.map((group) => (
                <div key={group.id} className="volunteer-group-list">
                    <h3 className="group-name-header">
                        {group.name}
                        <button
                            onClick={() => handleEditGroup(group.id, group.members)}
                            className="edit-group-button"
                        >
                            {t("volunteer.edit")}
                        </button>
                    </h3>
                    <table>
                        <thead>
                        <tr>
                            <th>{t("volunteer.username")}</th>
                            <th>{t("volunteer.firstName")}</th>
                            <th>{t("volunteer.lastName")}</th>
                            <th>{t("volunteer.status")}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {group.members.map((member) => (
                            <tr key={member.id}>
                                <td>{member.username}</td>
                                <td>{member.firstName}</td>
                                <td>{member.lastName}</td>
                                <td>{member.active ? t("volunteer.active") : t("volunteer.inactive")}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            ))}

            {isPopupVisible && (
                <div className="edit-group-modal">
                    <div className="popup-backdrop" onClick={closePopup}/>
                    <div className="popup">
                        <h3>{t("volunteer.editGroupMembers")}</h3>
                        <div className="volunteer-list">
                            <h4>{t("volunteer.allVolunteers")}</h4>
                            <ul>
                                {notMembers.map((volunteer) => (
                                    <li key={volunteer.id}>
                                        {volunteer.firstName} {volunteer.lastName} (
                                        {volunteer.username})
                                        <button
                                            onClick={() => handleAddMember(volunteer)}
                                            className="add-member-button"
                                        >
                                            {t("volunteer.add")}
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <div className="current-members">
                            <h4>{t("volunteer.currentMembers")}</h4>
                            <ul>
                                {groupMembers.map((member) => (
                                    <li key={member.id}>
                                        {member.firstName} {member.lastName} (
                                        {member.username})
                                        <button
                                            onClick={() => handleRemoveMember(member)}
                                            className="remove-member-button"
                                        >
                                            {t("volunteer.remove")}
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </div>
                        <button onClick={handleSaveGroup} className="save-group-button">
                            {t("volunteer.save")}
                        </button>
                        <button onClick={closePopup} className="close-popup-button">
                            {t("volunteer.close")}
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default VolunteerGroupList;
