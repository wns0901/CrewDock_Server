package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.dto.CreateChatRoomReqDTO;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.user.entity.User;

public interface ChatRoomService {
    ChatRoom createChatRoom(Long userId, String projectName);

    ChatRoom createChatRoom(CreateChatRoomReqDTO createChatRoomReqDTO);

    ChatRoomUser inviteUser(Project project, User receiver);
}
