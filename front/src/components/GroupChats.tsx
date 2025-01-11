import { Button } from "./ui/button";

const GroupChats = ({ chats }: any) => {
  // TODO: Add type for chats
  const handleCreateChat = async () => {
    const requestBody = {
        users: [1, 2],
    };
    const respone = await fetch("/api/chatrooms/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
    })
    if (respone.ok) {
        console.log("Chat created");
    } else {
        console.error("Failed to create chat");
    }
  }

  return (
    <div className="w-72">
      {chats.map((chat: any) => (
        <div key={chat.id}>
          <div>{chat.senderId}</div>
          <div>{chat.content}</div>
        </div>
      ))}
        <Button onClick={handleCreateChat}>Create Chat</Button>
    </div>
  );
};

export default GroupChats;
