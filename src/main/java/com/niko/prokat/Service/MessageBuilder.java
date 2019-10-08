package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Model.dto.ToolDto;
import com.niko.prokat.Model.dto.TulipDto;
import com.niko.prokat.Model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:server.properties")
public class MessageBuilder {
    private final UserService userService;

    @Value("${server.site.name}")
    private String siteName;

    @Value("${server.url.base}")
    private String serverBaseUrl;

    public String buildRegistrationMessage(Long id, String token){
        return "<h3 style='text-align:center'>Регистрация на "+siteName+
                "</h3><p>Для подтверждения" +
                " регистрации пожалуйтса перейдите по <a href='"+serverBaseUrl+
                "/activate-user/id/"+id+"/token/"+token+"'>ссылке</a></p>";
    }

    public String buildOrderMessage(OrderDto orderDto, String username,
                                    Integer total, Integer pledge){
        StringBuilder builder = new StringBuilder();
        builder.append("<h3 style='text-align:center'>Новый Заказ</h3>\n" +
                "<table style='border:1px solid black'>\n" +
                "    <tr>\n" +
                "        <th style='border:1px solid black'>Дата</th>\n" +
                "        <th style='border:1px solid black'>Кол-во дней</th>\n" +
                "        <th style='border:1px solid black'>Сумма аренды</th>\n" +
                "        <th style='border:1px solid black'>Залог</th>\n" +
                "        <th style='border:1px solid black'>Адрес</th>\n" +
                "        <th style='border:1px solid black'>Имя</th>\n" +
                "        <th style='border:1px solid black'>Фамилия</th>\n" +
                "        <th style='border:1px solid black'>Номер телефона</th>\n" +
                "    </tr>\n" +
                "    <tr>");
        builder.append("<td style='border:1px solid black'>").append(orderDto.getDate()).append("</td>");
        builder.append("<td style='border:1px solid black'>").append(orderDto.getFordays()).append("</td>");
        builder.append("<td style='border:1px solid black'>").append(total).append("</td>");
        builder.append("<td style='border:1px solid black'>").append(pledge).append("</td>");
        builder.append("<td style='border:1px solid black'>").append(orderDto.getAddress()).append("</td>");
        UserDto user = userService.getUser(username);
        builder.append("<td style='border:1px solid black'>").append(user.getFirstname()).append("</td>");
        builder.append("<td style='border:1px solid black'>").append(user.getLastname()).append("</td>");
        builder.append("<td style='border:1px solid black'>").append(user.getTelnum()).append("</td>");
        builder.append("</tr></table>");

        builder.append("<h3 style='text-align:center'>Инструменты</h3>\n" +
                "<table style='border:1px solid black'>\n" +
                "    <tr>\n" +
                "        <th style='border:1px solid black'>Брэнд</th>\n" +
                "        <th style='border:1px solid black'>Название</th>\n" +
                "        <th style='border:1px solid black'>Аренда</th>\n" +
                "        <th style='border:1px solid black'>Залог</th>\n" +
                "        <th style='border:1px solid black'>Вес</th>\n" +
                "        <th style='border:1px solid black'>Количество</th>\n" +
                "    </tr>");
        for (TulipDto<ToolDto, Integer> tool:orderDto.getUniqTools()) {
            builder.append( "<tr>");
            builder.append("<td style='border:1px solid black'>").append(tool.getFirst().getBrand().getName()).append("</td>");
            builder.append("<td style='border:1px solid black'>").append(tool.getFirst().getName()).append("</td>");
            builder.append("<td style='border:1px solid black'>").append(tool.getFirst().getPrice()).append("</td>");
            builder.append("<td style='border:1px solid black'>").append(tool.getFirst().getPledge()).append("</td>");
            builder.append("<td style='border:1px solid black'>").append(tool.getFirst().getWeight()).append("</td>");
            builder.append("<td style='border:1px solid black'>").append(tool.getSecond()).append("</td>");
            builder.append( "</tr>");
        }
        builder.append("</tr>\n" +
                "</table>");

        return builder.toString();
    }
}
