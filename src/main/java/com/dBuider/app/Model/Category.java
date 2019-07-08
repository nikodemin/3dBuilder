package com.dBuider.app.Model;

import com.dBuider.app.Service.TranslitService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Data
public class Category
{
    @Column(unique = true, nullable = false)
    private final String name;
    @Column(unique = true, nullable = false)
    private final String subcat;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getNamepath()
    {
        return TranslitService.toEng(name);
    }

    public String getSubcatpath()
    {
        return TranslitService.toEng(subcat);
    }
}
