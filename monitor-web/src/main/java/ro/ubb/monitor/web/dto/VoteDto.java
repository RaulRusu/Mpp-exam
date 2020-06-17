package ro.ubb.monitor.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class VoteDto extends BaseDto {
    private String CountyName;
    private int totalA;
    private int totalB;
    private int totalC;
    private int total;
}
