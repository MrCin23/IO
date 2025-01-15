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
import { ChatDB } from "@/types";
import CreateChatForm from "./CreateChatForm";

const GroupChats = ({
  chats,
  selectChat,
  setChats,
}: {
  chats: ChatDB[];
  selectChat: (chatId: string | null) => void;
  setChats: (chats: ChatDB[]) => void;
}) => {

 

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
           
            <CreateChatForm setChats={setChats} />
            <DialogClose asChild>
            
              <Button>Close</Button>
            </DialogClose>
            
          </div>
        </DialogContent>
      </Dialog>
      
    </div>
  );
};

export default GroupChats;
