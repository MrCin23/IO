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
import { useTranslation } from "react-i18next";
import { ScrollArea } from "./ui/scroll-area";
import axios from "@/api/Axios";
import { Label } from "./ui/label";

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
  const [filteredUsers, setFilteredUsers] = useState<Account[]>([]);
  const [filter, setFilter] = useState("");
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const { account } = useAccount();
  const initialChatData = chats.find((chat) => chat.id === chatId);

  if (!initialChatData) {
    console.log("Chat not found");
    return;
  }

  const { t } = useTranslation();

  const formSchema = z.object({
    chatName: z.string().nonempty().min(3).max(50),
    users: z.array(z.string()).refine((value) => value.some((user) => user), {
      message: t("update_chat_form.form_users_error_message"),
    }),
  });

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      chatName: initialChatData.name,
      users: initialChatData.users.map((userId) => String(userId)),
    },
  });

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

  useEffect(() => {
    axios.get("/auth").then((response) => setUsers(response.data));
    axios.get("/auth").then((response) => setFilteredUsers(response.data));
  }, [account]);

  const onSubmit = async (data: z.infer<typeof formSchema>) => {
    const requestBody = {
      id: chatId,
      users: data.users,
      name: data.chatName,
    };
    const response = await fetch(`/api/chatrooms/update`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    });
    if (response.ok) {
      const updatedChat: ChatDB = await response.json();
      const updatedChats = chats.map((chat) =>
        chat.id === chatId ? updatedChat : chat
      );
      setChats(updatedChats);
      setIsDialogOpen(false);
    } else {
      console.error("Failed to update chat");
    }
  };

  return (
    <Dialog
      open={isDialogOpen}
      onOpenChange={(isOpen) => setIsDialogOpen(isOpen)}
    >
      <DialogTrigger asChild>
        <Settings size={48} className="mr-4 mt-20 cursor-pointer" />
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>
            {t("update_chat_form.form dialog decsription")}
          </DialogTitle>
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
                    <Label>{t("update_chat_form.form_chat_label")}</Label>
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
                        {t("update_chat_form.form_users_description")}
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
                      <div className="space-y-2">
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
                                      className="p-0"
                                      id={user.id}
                                      checked={field.value?.includes(
                                        String(user.id)
                                      )}
                                      onCheckedChange={(checked) => {
                                        const userId = String(user.id);
                                        return checked
                                          ? field.onChange([
                                              ...field.value,
                                              userId,
                                            ])
                                          : field.onChange(
                                              field.value?.filter(
                                                (value) => value !== userId
                                              )
                                            );
                                      }}
                                    />
                                  </FormControl>
                                  <Label
                                    htmlFor={user.id}
                                    className="font-normal"
                                  >
                                    {user.firstName} {user.lastName}
                                  </Label>
                                </FormItem>
                              );
                            }}
                          />
                        ))}
                      </div>
                    </ScrollArea>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button type="submit" className="bg-white text-black">
                {t("general.update")}
              </Button>
            </form>
          </Form>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default UpdateChatForm;
