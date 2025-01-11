import { useState } from "react";
import { useWebSocket } from "../hooks/useWebSocket";
import { Message } from "../types";
import {Badge} from "@/components/ui/badge.tsx";
import {Input} from "@/components/ui/input.tsx";
import {ScrollArea} from "@/components/ui/scroll-area.tsx";
import {Button} from "@/components/ui/button.tsx";
import { Send } from 'lucide-react';

const Messages = ({selectedChat}: any) => {

  // const groupId = window.localStorage.getItem("groupId") ?? "1";
  const userId = 1;

  const { messages, sendMessage, isConnected, isLoading } =
    useWebSocket(selectedChat);
  const [content, setContent] = useState<string>("");

  const handleSendMessage = () => {
    if (content.trim()) {
      const message: Message = {
        senderName: "User", //TODO zamienic
        senderId: userId,
        chatId: selectedChat,
        content,
        timestamp: new Date(),
      };
      sendMessage(message);
      setContent("");
    }
  };

  const resolveMessageBadge = (id : number) => {
    if (userId === id) {
      return "bg-blue-500 text-white rounded-lg p-2 m-2 text-xl justify-end";
    } else {
      return "bg-gray-500 text-black rounded-lg p-2 m-2 text-xl justify-start";
    }
  }

  return (
      <div className="w-full h-full flex flex-col">
      <div className="w-full">
        <h2 className="text-center font-bold text-lg">Group Chat (Group ID: {selectedChat})</h2>
        <div>Status: {isConnected ? "Connected" : "Disconnected"}</div>
        {isLoading ? (
          <div>Loading messages...</div>
        ) : (
          <ScrollArea className="w-full flex-1 overflow-auto h-[700px]">
            <div>
            {messages.map((message, index) => (
              // zrobic key

              <div className={`flex w-full flex-col  ${userId === message.senderId ? "items-end " : "items-start"}`} key={`${index}`}>
                {/*<div>{message.senderId}</div>*/}
                <p className="mx-4">{message.senderName}</p>
                <Badge className={`rounded-lg p-2 mb-2 mx-4 text-lg ${userId === (message.senderId) ?
                "bg-blue-500 text-white justify-end" :
                "bg-gray-200 text-black justify-start"}
                `}>{message.content}</Badge>
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
        <Button className="rounded-full mx-5 bg-blue-500" onClick={handleSendMessage}><Send/></Button>
      </div>
    </div>
  );
};

export default Messages;
