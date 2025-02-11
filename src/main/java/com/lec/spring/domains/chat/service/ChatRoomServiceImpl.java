package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.dto.ChatRoomInfoResDTO;
import com.lec.spring.domains.chat.dto.CreateChatRoomReqDTO;
import com.lec.spring.domains.chat.dto.InviteMsgDTO;
import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.entity.ChatRoom;
import com.lec.spring.domains.chat.entity.ChatRoomUser;
import com.lec.spring.domains.chat.repository.ChatMessageRepository;
import com.lec.spring.domains.chat.repository.ChatRoomRepository;
import com.lec.spring.domains.chat.repository.ChatRoomUserRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.project.entity.ProjectMember;
import com.lec.spring.domains.project.repository.ProjectMemberRepository;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;
import com.lec.spring.global.config.redis.RedisUtil;
import com.lec.spring.global.config.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final RedisUtil redisUtil;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
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

            ChatMessage firstMessage = ChatMessage.builder()
                    .roomId(chatRoom.getId())
                    .msg("채팅방이 생성되었습니다.")
                    .sender("system")
                    .readUsers(new ArrayList<>())
                    .createdAt(LocalDateTime.now())
                    .build();

            chatMessageRepository.save(firstMessage);

            if (chatRoomUser.getId() == null) throw new IllegalArgumentException("Chat room user not created");

            return chatRoom;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ChatRoom createChatRoom(CreateChatRoomReqDTO createChatRoomReqDTO) {
        try {
            ChatRoom chatRoom = new ChatRoom(null, 2);

            chatRoomRepository.save(chatRoom);

            if (chatRoom.getId() == null) throw new IllegalArgumentException("Chat room not created");

            User sender = userRepository.findById(createChatRoomReqDTO.getSenderId())
                    .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

            User receiver = userRepository.findById(createChatRoomReqDTO.getReceiverId())
                    .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

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

            ChatMessage firstMessage = ChatMessage.builder()
                    .roomId(chatRoom.getId())
                    .msg("채팅방이 생성되었습니다.")
                    .sender("system")
                    .readUsers(new ArrayList<>())
                    .createdAt(LocalDateTime.now())
                    .build();

            chatMessageRepository.save(firstMessage);

            messagingTemplate.convertAndSend("/sub/room/" + chatRoom.getId(), firstMessage);

            if (chatRoomSenderUser.getId() == null || chatRoomReceiverUser.getId() == null) {
                throw new IllegalArgumentException("Chat room user not created");
            }

            return chatRoom;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ChatRoomUser inviteUser(Project project, User receiver) {
        try {
            ProjectMember projectCaptain = projectMemberRepository.findProjectCaptain(project.getId());

            User Captain = userRepository.findById(projectCaptain.getUserId()).orElseThrow(() -> new IllegalArgumentException("Captain not found"));

            ChatRoom chatRoom = chatRoomUserRepository.findByUser(Captain).getChatRoom();

            ChatRoomUser invitedChatRoomUser = ChatRoomUser.builder()
                    .user(receiver)
                    .chatRoom(chatRoom)
                    .roomName(project.getName() + " 채팅방")
                    .build();

            chatRoomUserRepository.save(invitedChatRoomUser);

            if (invitedChatRoomUser.getId() == null) throw new IllegalArgumentException("Chat room user not created");

            chatRoom.setUserCnt(chatRoom.getUserCnt() + 1);

            chatRoomRepository.save(chatRoom);

            InviteMsgDTO inviteMsgDTO = InviteMsgDTO.builder()
                    .roomId(chatRoom.getId())
                    .msg(receiver.getNickname() + "님이 초대되었습니다.")
                    .build();

            messagingTemplate.convertAndSend("/sub/user/" + receiver.getId(), inviteMsgDTO);

            ChatMessage inviteMessage = ChatMessage.builder()
                    .roomId(chatRoom.getId())
                    .msg(receiver.getNickname() + "님이 초대되었습니다.")
                    .sender("system")
                    .readUsers(new ArrayList<>())
                    .createdAt(LocalDateTime.now())
                    .build();

            chatMessageRepository.save(inviteMessage);

            messagingTemplate.convertAndSend("/sub/room/" + chatRoom.getId(), inviteMessage);

            return invitedChatRoomUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseEntity<?> getChatRoomList(Long userId) {
        try {
            PrincipalDetails userInfo = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String nickname = userInfo.getUser().getNickname();
            List<ChatRoomInfoResDTO> chatRoomInfos = chatRoomRepository.findChatRoomInfosByUserId(userId);
            chatRoomInfos.forEach(chatRoomInfo -> {
                ChatMessage lastMessage = chatMessageRepository.findTopByRoomIdOrderByCreatedAtDesc(chatRoomInfo.getId());
                if(lastMessage != null && lastMessage.getIsFirstMsg() == null) {
                    chatRoomInfo.setIsRead(lastMessage.getReadUsers().contains(nickname));
                    chatRoomInfo.setLastMessage(lastMessage);
                }
            });

            if( chatRoomInfos.size() > 1 ) {
                chatRoomInfos.sort((a, b) -> {
                    if (a.getLastMessage().getCreatedAt() == null) return -1;
                    if (b.getLastMessage().getCreatedAt() == null) return 1;
                    return b.getLastMessage().getCreatedAt().compareTo(a.getLastMessage().getCreatedAt());
                });
            }

            return ResponseEntity.ok(chatRoomInfos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Chat room list not found");
        }
    }

    @Override
    public ResponseEntity<?> leaveChatRoom(Long roomId, String nickname) {
        String readUsers = redisUtil.getData("room:" + roomId);

        if(readUsers == null || !readUsers.contains(nickname))
            return ResponseEntity.badRequest().body("User not found in chat room");

        List<String> list = new java.util.ArrayList<>(List.of(readUsers.split(",")));
        list.remove(nickname);

        if(list.isEmpty()) {
            redisUtil.deleteData("room:" + roomId);
        } else {
            redisUtil.setData("room:" + roomId, String.join(",", list));
        }

        System.out.println("readUsers: " + redisUtil.getData("room:" + roomId));

        return ResponseEntity.ok().body("User left chat room");
    }
}
