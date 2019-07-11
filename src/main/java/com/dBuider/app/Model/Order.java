package com.dBuider.app.Model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
public class Order
{
    private final String address;
    @OneToMany
    private final List<Tool> tools;
    @OneToOne
    private final User user;
    private final Date date;
    private final Integer fordays;
    private final boolean done;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public List<Pair<Tool,Integer>> getUniqtools()
    {
        List<Pair<Tool,Integer>> res = Arrays.stream(tools.toArray(Tool[]::new)).distinct()
                .map(t->Pair.of(t,0)).collect(Collectors.toList());

        tools.forEach(t->{
            Integer index = -1;
            for (int i = 0; i < res.size(); i++)
                if (res.get(i).getFirst().getId() == t.getId())
                {
                    index = i;
                    break;
                }

            if (index != -1)
                res.set(index,Pair.of(t,res.get(index).getSecond()+1));
        });

        return res;
    }
}
