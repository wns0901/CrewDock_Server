package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.dto.CreateChatRoomReqDTO;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import com.lec.spring.domains.chat.repository.ChatRoomRepository;
import com.lec.spring.domains.chat.repository.ChatRoomUserRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomUserRepository chatRoomUserRepository;

    private final UserRepository userRepository;

    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public ChatRoom createChatRoom(Long userId, String projectName) {
        try {
            ChatRoom chatRoom = new ChatRoom(null, 1);

            chatRoomRepository.save(chatRoom);

            if (chatRoom.getId() == null) throw new IllegalArgumentException("Chat room not created");

            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

            ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                    .user(user)
                    .chatRoom(chatRoom)
                    .roomName(projectName + " 채팅방")
                    .build();

            chatRoomUserRepository.save(chatRoomUser);

            if (chatRoomUser.getId() == null) throw new IllegalArgumentException("Chat room user not created");

            return chatRoom;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ChatRoom createChatRoom(CreateChatRoomReqDTO createChatRoomReqDTO) {
        ChatRoom chatRoom = new ChatRoom(null, 2);

        chatRoomRepository.save(chatRoom);

        if (chatRoom.getId() == null) throw new IllegalArgumentException("Chat room not created");

        User sender = userRepository.findById(createChatRoomReqDTO.getSenderId()).orElse(null);

        User receiver = userRepository.findById(createChatRoomReqDTO.getReceiverId()).orElse(null);

        ChatRoomUser chatRoomSenderUser = ChatRoomUser.builder()
                .user(sender)
                .chatRoom(chatRoom)
                .roomName(receiver.getNickname() + "님과의 채팅")
                .build();

        ChatRoomUser chatRoomReceiverUser = ChatRoomUser.builder()
                .user(receiver)
                .chatRoom(chatRoom)
                .roomName(sender.getNickname() + "님과의 채팅")
                .build();

        chatRoomUserRepository.saveAll(List.of(chatRoomSenderUser, chatRoomReceiverUser));

        return chatRoom;
    }

    @Override
    public ChatRoomUser inviteUser(Project project, Long userId) {
//        ProjectMember ch projectMemberRepository.findProjectCaptain(project.getId());

        return null;
    }
}
