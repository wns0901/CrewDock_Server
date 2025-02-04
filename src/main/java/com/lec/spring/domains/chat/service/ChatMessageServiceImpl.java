package com.lec.spring.domains.chat.service;

import com.lec.spring.domains.chat.dto.ChatMessageResDTO;
import com.lec.spring.domains.chat.entity.ChatMessage;
import com.lec.spring.domains.chat.repository.ChatMessageRepository;
import com.lec.spring.domains.chat.repository.ChatRoomRepository;
import com.lec.spring.domains.chat.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomUserRepository chatRoomUserRepository;

    private final SimpUserRegistry simpUserRegistry;

    @Override
    public ChatMessage save(Long roomId, ChatMessage chatMessage) {
        System.out.println(chatMessage);
        simpUserRegistry.getUsers().forEach(System.out::println);

        chatMessage.setRoomId(roomId);
        chatMessage.setReadUsers(List.of(chatMessage.getSender()));
        chatMessage.setCreatedAt(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public ResponseEntity<?> findAllByRoomId(Long roomId, String nickname) {
        try {

            List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId);
            List<ChatMessageResDTO> resChatMessages = new ArrayList<>();
            List<ChatMessageResDTO> lastReadChatMessage = new ArrayList<>();

            chatMessages.forEach((chatMessage) -> {
                ChatMessageResDTO resChatMessage = ChatMessageResDTO.of(chatMessage);
                if (!resChatMessage.getReadUsers().contains(nickname)) {
                    resChatMessage.getReadUsers().add(nickname);
                } else {
                    lastReadChatMessage.clear();
                    lastReadChatMessage.add(resChatMessage);
                }
                System.out.println(chatMessage);
                System.out.println(resChatMessage);
                chatMessageRepository.save(resChatMessage);
                resChatMessages.add(resChatMessage);
            });

            if (!lastReadChatMessage.isEmpty()) {
                lastReadChatMessage.get(0).setIsRead(true);
            }

            return ResponseEntity.ok(resChatMessages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }

    }

}
