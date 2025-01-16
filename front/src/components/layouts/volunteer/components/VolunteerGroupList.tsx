import { useEffect, useState } from "react";
import "../styles/VolunteerGroupList.css"; // Assuming you create a CSS file for styling
import { useTranslation } from "react-i18next";
import {VolunteerGroup} from "@/components/layouts/volunteer/models/VolunteerGroup.tsx";
import {Volunteer} from "@/components/layouts/volunteer/models/Volunteer.tsx";
import axios from "@/api/Axios.tsx";

function VolunteerGroupList() {
    // const [volunteerGroups, setVolunteerGroups] = useState<VolunteerGroup[]>([]);
    // const [isLoading, setIsLoading] = useState(false);
    // const { t } = useTranslation();
    //
    // // Fetch volunteer groups
    // // useEffect(() => {
    // //     setIsLoading(true);
    // //     axios
    // //         .get("/volunteerGroups")
    // //         .then((response) => setVolunteerGroups(response.data))
    // //         .catch((error) => console.error("Failed to fetch groups:", error))
    // //         .finally(() => setIsLoading(false));
    // // }, []);
    //
    const [newGroupName, setNewGroupName] = useState("");
    // // Handle form submission to add a new group
    const handleAddGroup = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!newGroupName.trim()) {
            alert(t("groupNameRequired")); // Use translation for error message
            return;
        }

        try {
            const response = await axios.post("/volunteerGroups", {groupName: newGroupName});
            setVolunteerGroups((prev) => [...prev, response.data]); // Add the new group to the list
            setNewGroupName(""); // Clear the input field
            alert(t("groupCreatedSuccessfully")); // Optional success message
        } catch (error) {
            console.error("Failed to create group:", error);
            alert(t("groupCreationFailed")); // Optional error message
        }
    };
    //
    // useEffect(() => {
    //     // Simulate loading state
    //     // setIsLoading(true);
    //
    //     // const [volunteers, setVolunteers] = useState<Volunteer[]>([]);
    //     // const [filter, setFilter] = useState('');
    //
    //     // Hardcoded volunteer group data
    //     setVolunteerGroups([
    //         {
    //             id: "1",
    //             name: "Team A",
    //             members: [
    //                 { id: "1", username: "johndoe", firstName: "John", lastName: "Doe", active: true },
    //                 { id: "2", username: "alexsmith", firstName: "Alex", lastName: "Smith", active: true },
    //             ],
    //         },
    //         {
    //             id: "2",
    //             name: "Team B",
    //             members: [
    //                 { id: "3", username: "janedoe", firstName: "Jane", lastName: "Doe", active: false },
    //             ],
    //         },
    //     ]);
    // }, []);

    const [volunteerGroups, setVolunteerGroups] = useState<VolunteerGroup[]>([]);
    const [volunteers, setVolunteers] = useState<Volunteer[]>([]);
    const [editingGroupId, setEditingGroupId] = useState<string | null>(null);
    const [groupMembers, setGroupMembers] = useState<Volunteer[]>([]);
    const { t } = useTranslation();

    // Fetch volunteer groups
    useEffect(() => {
        axios
            .get("/volunteerGroups")
            .then((response) => setVolunteerGroups(response.data))
            .catch((error) => console.error("Failed to fetch groups:", error));
    }, []);

    // Fetch all volunteers
    useEffect(() => {
        axios
            .get("/volunteers")
            .then((response) => setVolunteers(response.data))
            .catch((error) => console.error("Failed to fetch volunteers:", error));
    }, []);

    // Open the editor for a specific group
    const handleEditGroup = (groupId: string, currentMembers: Volunteer[]) => {
        setEditingGroupId(groupId);
        setGroupMembers(currentMembers); // Set the current members of the group
    };

    // Add a member to the group
    const handleAddMember = (volunteer: Volunteer) => {
        if (!groupMembers.find((member) => member.id === volunteer.id)) {
            setGroupMembers((prev) => [...prev, volunteer]);
        }
    };

    // Remove a member from the group
    const handleRemoveMember = (volunteerId: string) => {
        setGroupMembers((prev) => prev.filter((member) => member.id !== volunteerId));
    };

    // Save the updated group
    const handleSaveGroup = async () => {
        try {
            const updatedGroup = {
                id: editingGroupId,
                members: groupMembers.map((member) => member.id),
            };
            console.log(editingGroupId)
            await axios.post(`/volunteerGroups/${editingGroupId}/addMembers`, updatedGroup);
            setVolunteerGroups((prev) =>
                prev.map((group) =>
                    group.id === editingGroupId ? { ...group, members: groupMembers } : group
                )
            );

            setEditingGroupId(null);
            alert(t("groupUpdatedSuccessfully"));
        } catch (error) {
            console.error("Failed to save group:", error);
            alert(t("groupUpdateFailed"));
        }
    };

    return (
        <div className="volunteer-group-list-container">
            <h2>{t("volunteerGroupListTitle")}</h2>

            <form onSubmit={handleAddGroup} className="add-group-form">
                <input
                    type="text"
                    value={newGroupName}
                    onChange={(e) => setNewGroupName(e.target.value)}
                    placeholder={t("enterGroupName")}
                    className="group-input"
                />
                <button type="submit" className="add-group-button">
                    {t("addGroup")}
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
                            {t("edit")}
                        </button>
                    </h3>
                    <table>
                        <thead>
                        <tr>
                            <th>{t("username")}</th>
                            <th>{t("firstName")}</th>
                            <th>{t("lastName")}</th>
                            <th>{t("status")}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {group.members.map((member) => (
                            <tr key={member.id}>
                                <td>{member.username}</td>
                                <td>{member.firstName}</td>
                                <td>{member.lastName}</td>
                                <td>{member.active ? t("active") : t("inactive")}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            ))}

            {editingGroupId && (
                <div className="edit-group-modal">
                    <h3>{t("editGroupMembers")}</h3>
                    <div className="volunteer-list">
                        <h4>{t("allVolunteers")}</h4>
                        <ul>
                            {volunteers.map((volunteer) => (
                                <li key={volunteer.id}>
                                    {volunteer.firstName} {volunteer.lastName} (
                                    {volunteer.username})
                                    <button
                                        onClick={() => handleAddMember(volunteer)}
                                        className="add-member-button"
                                    >
                                        {t("add")}
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div className="current-members">
                        <h4>{t("currentMembers")}</h4>
                        <ul>
                            {groupMembers.map((member) => (
                                <li key={member.id}>
                                    {member.firstName} {member.lastName} (
                                    {member.username})
                                    <button
                                        onClick={() => handleRemoveMember(member.id)}
                                        className="remove-member-button"
                                    >
                                        {t("remove")}
                                    </button>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <button onClick={handleSaveGroup} className="save-group-button">
                        {t("save")}
                    </button>
                </div>
            )}
        </div>
    );


    // return (
    //     <div className="volunteer-group-list-container">
    //         <h2>{t("volunteerGroupListTitle")}</h2>
    //
    //         {/* Add New Group Form */}
    //         <form onSubmit={handleAddGroup} className="add-group-form">
    //             <input
    //                 type="text"
    //                 value={newGroupName}
    //                 onChange={(e) => setNewGroupName(e.target.value)}
    //                 placeholder={t("enterGroupName")}
    //                 className="group-input"
    //             />
    //             <button type="submit" className="add-group-button">
    //                 {t("addGroup")}
    //             </button>
    //         </form>
    //
    //         {isLoading && <p>{t("loading")}</p>}
    //         {!isLoading && volunteerGroups.length === 0 && (
    //             <p>{t("noVolunteerGroups")}</p>
    //         )}
    //         {!isLoading && volunteerGroups.length > 0 && (
    //             <div className="volunteer-group-list">
    //                 {volunteerGroups.map((group) => (
    //                     <div key={group.id} className="volunteer-group">
    //                         <h3>{group.name}</h3>
    //                         <table>
    //                             <thead>
    //                                 <tr>
    //                                     <th>{t("username")}</th>
    //                                     <th>{t("firstName")}</th>
    //                                     <th>{t("lastName")}</th>
    //                                     <th>{t("status")}</th>
    //                                 </tr>
    //                             </thead>
    //                             <tbody>
    //                                 {group.members.map((member) => (
    //                                     <tr key={member.id}>
    //                                         <td>{member.username}</td>
    //                                         <td>{member.firstName}</td>
    //                                         <td>{member.lastName}</td>
    //                                         <td>
    //                                             {member.active
    //                                                 ? t("active")
    //                                                 : t("inactive")}
    //                                         </td>
    //                                     </tr>
    //                                 ))}
    //                             </tbody>
    //                         </table>
    //                     </div>
    //                 ))}
    //             </div>
    //         )}
    //     </div>
    // );
}

export default VolunteerGroupList;
