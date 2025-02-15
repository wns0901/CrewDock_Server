package com.lec.spring.domains.calendar.reponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lec.spring.domains.calendar.dto.HolidaysDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayApiResponse {
    @JsonProperty("response")
    private ApiResponse response;

    public Header getHeader() {
        return response.getHeader();
    }

    public Body getBody() {
        return response.getBody();
    }

    @Data
    public static class ApiResponse {
        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;
    }

    @Data
    @NoArgsConstructor
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Data
    @NoArgsConstructor
    public static class Body {
        @JsonDeserialize(using = ItemsDeserializer.class)
        private List<Item> items;
        @JsonProperty("numOfRows")
        private int numOfRows;
        @JsonProperty("pageNo")
        private int pageNo;
        @JsonProperty("totalCount")
        private int totalCount;
    }

    @Data
    @NoArgsConstructor
    public static class Item {
        @JsonProperty("dateKind")
        private String dateKind;
        @JsonProperty("dateName")
        private String dateName;
        @JsonProperty("isHoliday")
        private String isHoliday;
        @JsonProperty("locdate")
        private int locdate;
        @JsonProperty("seq")
        private int seq;
    }

    /**
     * items 필드의 JSON 배열을 List<Item>으로 변환하는 Deserializer<br>
     * API에서 결과 값을 반환할 떄, 공휴일이 1개라면 '{}'로 반환하고, 2개 이상이면 '[]'로 반환하기에 별도의 Deserializer가 필요함.
     */
    public static class ItemsDeserializer extends JsonDeserializer<List<Item>> {
        @Override
        public List<Item> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            List<Item> items = new ArrayList<>();
            ObjectMapper mapper = (ObjectMapper) p.getCodec();

            if (node instanceof ObjectNode) {
                // 단일 객체 처리
                items.add(mapper.treeToValue(node.get("item"), Item.class));
            } else if (node instanceof ArrayNode) {
                // 배열 처리
                for (JsonNode itemNode : (ArrayNode) node.get("item")) {
                    items.add(mapper.treeToValue(itemNode, Item.class));
                }
            }
            return items;
        }
    }
}