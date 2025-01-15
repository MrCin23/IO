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

const formSchema = z.object({
  chatName: z.string().nonempty().min(3).max(50),
  users: z.array(z.string()).refine((value) => value.some((user) => user), {
    message: "You have to select at least one item.",
  }),
});

const CreateChatForm = ({setChats} : {setChats: (chats: ChatDB[]) => void} ) => {
  const [users, setUsers] = useState<Account[]>([]);
  const { account } = useAccount();

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      users: [],
    },
  });

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        // TODO odkomentowac jak bedzie dzialac
        // if (account === null) {
        //   console.error("Account is null");
        //   return;
        // }

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
        users: data.users,
        name: data.chatName,
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
    };

  return (
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
                  Select users to add to the chat.
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
                            checked={field.value?.includes(String(user.id))}
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
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
};

export default CreateChatForm;
