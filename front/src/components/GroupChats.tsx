import { Button } from "./ui/button";

const GroupChats = ({ chats, selectChat }: any) => {
  // TODO: Add type for chats
  const handleCreateChat = async () => {
    const requestBody = {
      users: [1, 2],
      name: "Testowy chat"
    };
    const respone = await fetch("/api/chatrooms/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    });
    if (respone.ok) {
      console.log("Chat created");
    } else {
      console.error("Failed to create chat");
    }
  };

  return (
    <div className="w-72 border-r border-black flex flex-col justify-between p-4">
      <div>
        {chats.map((chat: any) => (
          <div key={chat.id} onClick={() => selectChat(chat.id)}>
            <div >Chat ID: {chat.id}</div>
            <div >Chat Name: {chat.name}</div>
          </div>
        ))}
      </div>
      <Button onClick={handleCreateChat}>Create Chat</Button>
    </div>
  );
};

export default GroupChats;
