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
import { ScrollArea } from "./ui/scroll-area";
import { useTranslation } from "react-i18next";
import axios from "@/api/Axios";

const CreateChatForm = ({
  setChats,
  setIsDialogOpen,
}: {
  setChats: (chats: ChatDB[]) => void;
  setIsDialogOpen: (isOpen: boolean) => void;
}) => {
  const [users, setUsers] = useState<Account[]>([]);
  const [filteredUsers, setFilteredUsers] = useState<Account[]>([]);
  const [filter, setFilter] = useState("");
  const { account } = useAccount();
  const { t } = useTranslation();

  const formSchema = z.object({
    chatName: z.string().nonempty().min(3).max(50),
    users: z.array(z.string()).refine((value) => value.some((user) => user), {
      message: t("create_chat_form.form_users_error_message"),
    }),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      users: [],
    },
  });

  useEffect(() => {
    axios.get("/auth").then((response) => setUsers(response.data));
    axios.get("/auth").then((response) => setFilteredUsers(response.data));
  }, [account]);

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.toLowerCase();
    setFilter(value);

    setFilteredUsers(
      users.filter(
        (user) =>
          user.firstName.toLowerCase().includes(value) ||
          user.lastName.toLowerCase().includes(value)
      )
    );
  };

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
      setIsDialogOpen(false);
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
              <FormLabel>{t("create_chat_form.form_chat_label")}</FormLabel>
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
                  {t("create_chat_form.form_users_description")}
                </FormDescription>
              </div>
              <div className="mb-4">
                <Input
                  placeholder={t(
                    "create_chat_form.form_users_input_placeholder"
                  )}
                  value={filter}
                  onChange={handleFilterChange}
                />
              </div>
              <ScrollArea className="h-40 border rounded-md p-2">
                {filteredUsers.map((user) => (
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
                              id={user.id}
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
                          <FormLabel htmlFor={user.id} className="font-normal">
                            {user.firstName} {user.lastName}
                          </FormLabel>
                        </FormItem>
                      );
                    }}
                  />
                ))}
              </ScrollArea>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">{t("general.submit")}</Button>
      </form>
    </Form>
  );
};

export default CreateChatForm;
