import { z } from "zod";

import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";

import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Send } from "lucide-react";
import { Message } from "@/types";
import { useTranslation } from "react-i18next";

const formSchema = z.object({
  message: z.string().min(2).max(50),
});
const SendMessageForm = ({
  sendMessage,
  username,
  userId,
  selectedChat,
}: any) => {
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      message: "",
    },
  });

  const { t } = useTranslation();

  function onSubmit(values: z.infer<typeof formSchema>) {
    if (values.message.trim()) {
      const message: Message = {
        senderName: username,
        senderId: userId,
        chatId: selectedChat,
        content: values.message,
        timestamp: new Date(),
      };
      sendMessage(message);
      form.setValue("message", "");
    }
  }
  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex w-full items-center"
      >
        <FormField
          control={form.control}
          name="message"
          render={({ field }) => (
            <FormItem className="flex-grow px-6">
              <FormControl>
                <Input
                  placeholder={t(
                    "send_message_form.form_message_input_placeholder"
                  )}
                  className="w-full"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button className="rounded-full bg-blue-500" type="submit">
          <Send />
        </Button>
      </form>
    </Form>
  );
};

export default SendMessageForm;
