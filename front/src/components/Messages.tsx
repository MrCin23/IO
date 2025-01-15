import { useWebSocket } from "../hooks/useWebSocket";
import { Badge } from "@/components/ui/badge.tsx";
import { ScrollArea } from "@/components/ui/scroll-area.tsx";
import SendMessageForm from "./SendMessageForm";
import { useTranslation } from "react-i18next";
import { ArrowLeft } from "lucide-react";

const Messages = ({ selectedChat, userId, username, chatName }: any) => {
  const { messages, sendMessage, isConnected, isLoading } =
    useWebSocket(selectedChat);

  const { t } = useTranslation();

  return (
    <div className="w-full h-screen flex flex-col justify-between py-8 px-6 relative">
      {/* musi byc przez a bo my resetujemy style i home page nie wyglada jak ma wygladac */}
      <a href="/" className="absolute top-6 left-6 cursor-pointer">
        <ArrowLeft />
      </a>
      <div className="w-full">
        <h2 className="text-center font-bold text-lg">{chatName}</h2>
        <div>
          Status:{" "}
          {isConnected
            ? t("messages.connected_status")
            : t("messages.disconnected_status")}
        </div>
        {isLoading ? (
          <div>{t("messages.loading")}</div>
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
      {chatName !== "No chat selected" && (
        <SendMessageForm
          sendMessage={sendMessage}
          username={username}
          userId={userId}
          selectedChat={selectedChat}
        />
      )}
    </div>
  );
};

export default Messages;
