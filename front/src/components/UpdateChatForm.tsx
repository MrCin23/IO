import { useAccount } from "@/contexts/uwierzytelnianie/AccountContext";
import { useEffect, useState } from "react";
import { Account } from "@/models/uwierzytelnianie/Account";
import { Checkbox } from "./ui/checkbox";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormMessage,
} from "./ui/form";
import { FormLabel } from "@mui/material";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { ChatDB } from "@/types";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Settings } from "lucide-react";

const formSchema = z.object({
  chatName: z.string().nonempty().min(3).max(50),
  users: z.array(z.string()).refine((value) => value.some((user) => user), {
    message: "You have to select at least one item.",
  }),
});

const UpdateChatForm = ({
  chatId,
  chats,
  setChats,
}: {
  chatId: string;
  chats: ChatDB[];
  setChats: (chats: ChatDB[]) => void;
}) => {
  const [users, setUsers] = useState<Account[]>([]);
  const { account } = useAccount();
  const initialChatData = chats.find((chat) => chat.id === chatId);

  if (!initialChatData) {
    throw new Error("Chat not found");
  }

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      chatName: initialChatData.name,
      users: initialChatData.users.map((userId) => String(userId)),
    },
  });

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch(`/api/auth`);

        if (!response.ok) {
          throw new Error("Failed to fetch users");
        }

        const data = await response.json();
        setUsers(data);
      } catch (err) {
        console.error("Error fetching users from DB:", err);
      }
    };

    fetchUsers();
  }, [account]);

  const onSubmit = async (data: z.infer<typeof formSchema>) => {
    const requestBody = {
      id: chatId,
      users: data.users,
      name: data.chatName,
    };
    console.log(requestBody);
    const response = await fetch(`/api/chatrooms/update`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    });
    if (response.ok) {
      console.log("Chat updated");
      const updatedChat: ChatDB = await response.json();
      const updatedChats = chats.map((chat) =>
        chat.id === chatId ? updatedChat : chat
      );
      setChats(updatedChats);
    } else {
      console.error("Failed to update chat");
    }
  };

  return (
    <Dialog>
      <DialogTrigger asChild>
          <Settings color="black" size={48} className="mr-4 mt-4"/>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Please provide a chat name</DialogTitle>
          <DialogDescription></DialogDescription>
        </DialogHeader>
        <div className="space-y-2">
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
              <FormField
                control={form.control}
                name="chatName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Chat Name</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="users"
                render={() => (
                  <FormItem>
                    <div className="mb-4">
                      <FormDescription>
                        Select users to update the chat.
                      </FormDescription>
                    </div>
                    {users.map((user) => (
                      <FormField
                        key={user.id}
                        control={form.control}
                        name="users"
                        render={({ field }) => {
                          return (
                            <FormItem
                              key={user.id}
                              className="flex flex-row items-start space-x-3 space-y-0"
                            >
                              <FormControl>
                                <Checkbox
                                  checked={field.value?.includes(
                                    String(user.id)
                                  )}
                                  onCheckedChange={(checked) => {
                                    const userId = String(user.id);
                                    return checked
                                      ? field.onChange([...field.value, userId])
                                      : field.onChange(
                                          field.value?.filter(
                                            (value) => value !== userId
                                          )
                                        );
                                  }}
                                />
                              </FormControl>
                              <FormLabel className="font-normal">
                                {user.firstName} {user.lastName}
                              </FormLabel>
                            </FormItem>
                          );
                        }}
                      />
                    ))}
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button type="submit">Update</Button>
            </form>
          </Form>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default UpdateChatForm;
