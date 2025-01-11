const GroupChats = ({ chats }: any) => {
  // TODO: Add type for chats
  return (
    <div className="w-72">
      {chats.map((chat: any) => (
        <div key={chat.id}>
          <div>{chat.senderId}</div>
          <div>{chat.content}</div>
        </div>
      ))}
    </div>
  );
};

export default GroupChats;
