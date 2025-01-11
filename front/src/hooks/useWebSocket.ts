import { useEffect, useRef, useState } from "react";
import { Client } from "@stomp/stompjs";
import { Message } from "../types";

export const useWebSocket = (groupId: number) => {
  const clientRef = useRef<Client | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const [isConnected, setIsConnected] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const fetchInitialMessages = async () => {
    try {
      setIsLoading(true);
      const response = await fetch(`/api/chatrooms/${groupId}/history`);
      if (!response.ok) {
        throw new Error("Failed to fetch messages");
      }
      const data: Message[] = await response.json();
      setMessages(data);
    } catch (err) {
      console.error("Error fetching messages from DB:", err);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchInitialMessages();

    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
      onConnect: () => {
        console.log("WebSocket connected");
        setIsConnected(true);

        client.subscribe(`/topic/group/${groupId}`, (message) => {
          const receivedMessage: Message = JSON.parse(message.body);
          setMessages((prev) => [...prev, receivedMessage]);
        });
      },
      onDisconnect: () => {
        console.log("WebSocket disconnected");
        setIsConnected(false);
      },
    });

    client.activate();
    clientRef.current = client;

    return () => {
      client.deactivate();
    };
  }, [groupId]);

  const sendMessage = (message: Message) => {
    if (clientRef.current?.connected) {
      clientRef.current.publish({
        destination: `/app/group/${groupId}`,
        body: JSON.stringify(message),
      });
    } else {
      console.error("WebSocket client is not connected");
    }
  };

  return { messages, sendMessage, isConnected, isLoading };
};
