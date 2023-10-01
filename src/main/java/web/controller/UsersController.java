package web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.ServiceDao;

@Controller
@RequestMapping("/people")
public class UsersController {

    private final ServiceDao serviceDao;

    @Autowired
    public UsersController(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @GetMapping
    public String index(Model model) { //Получим всех людей из Dao и передадим на отображение в представление
        model.addAttribute("users", serviceDao.index());

        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //Получим одного человека по id из Dao и передадим на отображение в представление
        model.addAttribute("user", serviceDao.show(id));

        return "users/show";
    }

    @GetMapping("/new") //Получим форму для добавления нового человека
    public String newUser(Model model) {
        model.addAttribute("user", new User()); //Эту строку можно убрать добавив аннотацию в параметр (@ModelAttribute("user") User user) - Создается пустой пользователь и помещается в модель

        return "users/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) { // Добавляем нового человека в БД из формы по адресу people/new. После чего нас перенаправляют на страницу /people со всеми пользователями

        if (bindingResult.hasErrors())
            return "users/new";

        serviceDao.save(user);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", serviceDao.show(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "users/edit";

        serviceDao.update(user, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        serviceDao.delete(id);
        return "redirect:/people";
    }

}
