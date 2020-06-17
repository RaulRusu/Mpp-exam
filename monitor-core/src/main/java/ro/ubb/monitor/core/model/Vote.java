package ro.ubb.monitor.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Vote extends BaseEntity<Long> {
    private String CountyName;
    private int totalA;
    private int totalB;
    private int totalC;
    private int total;
}
