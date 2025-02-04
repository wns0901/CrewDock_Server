package com.lec.spring.domains.chat.dto;

import com.lec.spring.domains.chat.entity.ChatMessage;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ChatMessageResDTO extends ChatMessage {
    private Boolean isRead;

    public static ChatMessageResDTO  of(ChatMessage chatMessage) {
        return ChatMessageResDTO.builder()
                .id(chatMessage.getId())
                .roomId(chatMessage.getRoomId())
                .msg(chatMessage.getMsg())
                .sender(chatMessage.getSender())
                .createdAt(chatMessage.getCreatedAt())
                .readUsers(chatMessage.getReadUsers())
                .build();
    }
}
