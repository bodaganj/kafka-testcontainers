package com.bodaganj.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestMessage {

   private final String id;
   private final String name;
   private final String surname;
}
