import GroupChats from "../components/GroupChats";
import Messages from "../components/Messages";

const Chat = () => {
  return (
    <div className="flex w-screen h-screen">
      <GroupChats chats={[]} /> <Messages />
    </div>
  );
};

export default Chat;
