import { ChatDB } from "@/types";
import GroupChats from "../components/GroupChats";
import Messages from "../components/Messages";
import { useEffect, useState } from "react";
import { useAccount } from "@/contexts/uwierzytelnianie/AccountContext";

const Chat = () => {
  document.getElementById("root")!.style.padding = "0";
  document.getElementById("root")!.style.margin = "0";

  const [selectedChat, setSelectedChat] = useState<string | null>(null);
  const [chats, setChats] = useState<ChatDB[]>([]);

  const {account} = useAccount();
  console.log(account);

  useEffect(() => {
    const fetchChats = async () => {
      try {
        if (account === null) {
          console.error("Account is null");
          return;
        }

        const response = await fetch(`/api/chatrooms/user/${account.id}`); 
        if (!response.ok) {
          throw new Error("Failed to fetch chats");
        }
        const data = await response.json();
        console.log(data);
        setChats(data);
      } catch (err) {
        console.error("Error fetching chats from DB:", err);
      }
    }

    fetchChats();
  }, [account, selectedChat])

 

  return (
    <div className="flex w-screen h-screen">
      <GroupChats chats={chats} selectChat={setSelectedChat} setChats={setChats} />
      <Messages selectedChat={selectedChat} userId={account?.id} username={account?.username}/>
    </div>
  );
};

export default Chat;
