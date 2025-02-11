package com.lec.spring.domains.chat.dto;

import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.entity.ChatRoom;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ChatRoomInfoResDTO extends ChatRoom {

    public ChatRoomInfoResDTO(ChatRoom chatRoom, String roomName) {
        super(chatRoom.getId(), chatRoom.getUserCnt());
        this.roomName = roomName;
    }

    String roomName;

    ChatMessage lastMessage;

    Boolean isRead;

}
