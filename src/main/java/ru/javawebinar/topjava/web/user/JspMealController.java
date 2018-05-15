package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.Util.orElse;

@Controller
public class JspMealController {
    @Autowired
    private MealService mealService;

    @PostMapping
    public String Post(@RequestParam Map<String, String> queryMap, Model model)  {
        String action = queryMap.get("action");
        int userId = AuthorizedUser.id();
        if (action == null) {
            Meal meal = new Meal(
                    LocalDateTime.parse(queryMap.get("dateTime")),
                    queryMap.get("description"),
                    Integer.parseInt(queryMap.get("calories")));

            if (queryMap.get("id")==null) {
                mealService.create(meal, userId);
            } else {
                mealService.update(meal, getId(queryMap));
            }

        } else if ("filter".equals(action)) {
            LocalDate startDate = parseLocalDate(queryMap.get("startDate"));
            LocalDate endDate = parseLocalDate(queryMap.get("endDate"));
            LocalTime startTime = parseLocalTime(queryMap.get("startTime"));
            LocalTime endTime = parseLocalTime(queryMap.get("endTime"));
            List<Meal> mealsDateFiltered = mealService.getBetweenDates(startDate, endDate, userId);

            model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(mealsDateFiltered, AuthorizedUser.getCaloriesPerDay(),
                    orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX)));
        }
        return "meals";
    }

    @GetMapping("/meals")
    public String meals(@RequestParam Map<String, String> queryMap, Model model) {
        String action = queryMap.get("action");
        int userId = AuthorizedUser.id();

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(queryMap);
                mealService.delete(id, userId);
                model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealService.get(getId(queryMap), userId);
                model.addAttribute("meal", meal);
                return "mealForm";
            case "all":
            default:
                model.addAttribute("meals", MealsUtil.getWithExceeded(mealService.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
                break;
        }
        return "meals";
    }

    private int getId(Map<String, String> queryMap) {
        String paramId = Objects.requireNonNull(queryMap.get("id"));
        return Integer.parseInt(paramId);
    }
}
