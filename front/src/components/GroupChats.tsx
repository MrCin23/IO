import { Button } from "./ui/button";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Label } from "./ui/label";
import { Input } from "./ui/input";
import { useState } from "react";
import { ChatDB } from "@/types";

const GroupChats = ({
  chats,
  selectChat,
  setChats,
}: {
  chats: ChatDB[];
  selectChat: (chatId: string | null) => void;
  setChats: (chats: ChatDB[]) => void;
}) => {
  const [chatName, setChatName] = useState<string>("");
  const handleCreateChat = async () => {
    const requestBody = {
      users: [1, 2], // Get current User ID from context
      name: chatName,
    };
    const response = await fetch("/api/chatrooms/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    });
    if (response.ok) {
      console.log("Chat created");
      const data: ChatDB = await response.json();
      // @ts-ignore // TODO: Fix this later
      setChats((prev) => [...prev, data]);
    } else {
      console.error("Failed to create chat");
    }
    setChatName("");
  };

  return (
    <div className="w-72 border-r border-black flex flex-col justify-between p-4">
      <div>
        {chats.map((chat) => (
          <div key={chat.id} onClick={() => selectChat(chat.id)}>
            <div>Chat ID: {chat.id}</div>
            <div>Chat Name: {chat.name}</div>
          </div>
        ))}
      </div>
      <Dialog>
        <DialogTrigger asChild>
          <Button>Create a new Chat</Button>
        </DialogTrigger>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Please provide a chat name</DialogTitle>
            <DialogDescription>
              This action cannot be undone. You won't be able to change that
              later.
            </DialogDescription>
          </DialogHeader>
          <div>
            <Label>Name</Label>
            <Input
              value={chatName}
              onChange={(e) => setChatName(e.target.value)}
            />
            <DialogClose>
              <Button onClick={() => handleCreateChat()}>Create Chat</Button>
            </DialogClose>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default GroupChats;
