import { Button } from "./ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { ChatDB } from "@/types";
import CreateChatForm from "./CreateChatForm";
import { Users } from "lucide-react";
import { useState } from "react";

const GroupChats = ({
  chats,
  selectChat,
  setChats,
  selectedChat,
}: {
  chats: ChatDB[];
  selectChat: (chatId: string | null) => void;
  setChats: (chats: ChatDB[]) => void;
  selectedChat: string | null;
}) => {
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  return (
    <div className="w-72 border-r bg-gray-900 text-white border-black flex flex-col justify-between p-4">
      <div className="space-y-4">
        {chats.map((chat) => (
          <div
            key={chat.id}
            onClick={() => selectChat(chat.id)}
            className={`flex items-center p-3 space-x-4 rounded-lg cursor-pointer ${
              selectedChat === chat.id ? "bg-gray-700" : "hover:bg-gray-800"
            } `}
          >
            <Users size={20} />
            <span>{chat.name}</span>
          </div>
        ))}
      </div>
      <Dialog
        open={isDialogOpen}
        onOpenChange={(isOpen) => setIsDialogOpen(isOpen)}
      >
        <DialogTrigger asChild>
          <Button onClick={() => setIsDialogOpen(true)} className="bg-blue-500">
            Create a new Chat
          </Button>
        </DialogTrigger>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Please provide a chat name</DialogTitle>
            <DialogDescription>
            </DialogDescription>
          </DialogHeader>
          <div className="space-y-2">
            <CreateChatForm
              setChats={setChats}
              setIsDialogOpen={setIsDialogOpen}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default GroupChats;
