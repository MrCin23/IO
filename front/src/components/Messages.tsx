import { useState } from "react";
import { useWebSocket } from "../hooks/useWebSocket";
import { Message } from "../types";
import { Badge } from "@/components/ui/badge.tsx";
import { Input } from "@/components/ui/input.tsx";
import { ScrollArea } from "@/components/ui/scroll-area.tsx";
import { Button } from "@/components/ui/button.tsx";
import { Send } from "lucide-react";

const Messages = ({ selectedChat, userId, username }: any) => {
  const { messages, sendMessage, isConnected, isLoading } =
    useWebSocket(selectedChat);
  const [content, setContent] = useState<string>("");

  const handleSendMessage = () => {
    if (content.trim()) {
      const message: Message = {
        senderName: username,
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
    <div className="w-full h-screen flex flex-col justify-between py-8">
      <div className="w-full">
        <h2 className="text-center font-bold text-lg">
          Group Chat (Group ID: {selectedChat})
        </h2>
        <div>Status: {isConnected ? "Connected" : "Disconnected"}</div>
        {isLoading ? (
          <div>Loading messages...</div>
        ) : (
          <ScrollArea className="w-full flex-1 overflow-auto h-full">
            <div>
              {messages.map((message, index) => (
                <div
                  className={`flex w-full flex-col  ${
                    userId === message.senderId ? "items-end " : "items-start"
                  }`}
                  key={`${index}`}
                >
                  <p className="mx-4">{message.senderName}</p>
                  <Badge
                    className={`rounded-lg p-2 mb-2 mx-4 text-lg ${
                      userId === message.senderId
                        ? "bg-blue-500 text-white justify-end"
                        : "bg-gray-200 text-black justify-start"
                    }
                `}
                  >
                    {message.content}
                  </Badge>
                </div>
              ))}
            </div>
          </ScrollArea>
        )}
      </div>
      <div className="flex w-full mt-5 ">
        <Input
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Type a message"
          className="w-full ml-5"
        />
        <Button
          className="rounded-full mx-5 bg-blue-500"
          onClick={handleSendMessage}
        >
          <Send />
        </Button>
      </div>
    </div>
  );
};

export default Messages;
