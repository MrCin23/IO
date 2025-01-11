import { useState } from "react";
import { useWebSocket } from "../hooks/useWebSocket";
import { Message } from "../types";

const Messages = ({selectedChat}: any) => {

  // const groupId = window.localStorage.getItem("groupId") ?? "1";
  const userId = 1;

  const { messages, sendMessage, isConnected, isLoading } =
    useWebSocket(selectedChat);
  const [content, setContent] = useState<string>("");

  const handleSendMessage = () => {
    if (content.trim()) {
      const message: Message = {
        senderId: userId,
        chatId: selectedChat,
        content,
        timestamp: new Date(),
      };
      sendMessage(message);
      setContent("");
    }
  };
  return (
    <div>
      <div>
        <h2>Group Chat (Group ID: {selectedChat})</h2>
        <div>Status: {isConnected ? "Connected" : "Disconnected"}</div>
        {isLoading ? (
          <div>Loading messages...</div>
        ) : (
          <div>
            {messages.map((message, index) => (
              // zrobic key
              <div key={`${index}`}>
                <div>{message.senderId}</div>
                <div>{message.content}</div>
              </div>
            ))}
          </div>
        )}
      </div>
      <div>
        <input
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Type a message"
        />
        <button onClick={handleSendMessage}>Send</button>
      </div>
    </div>
  );
};

export default Messages;
