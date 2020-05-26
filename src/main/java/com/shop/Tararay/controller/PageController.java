package com.shop.Tararay.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/")
public class PageController {

    private static final String TEMPLATE_LOCATION = "shop/template";
    private static final String PAGE_KEY = "bodyPage";
    private static final String REDIRECT_TO = "redirect:";
    private static final String SHOPPING_PAGE_LOCATION = "shop/shopping";
    private static final String CREATE_ITEM_PAGE_LOCATION = "shop/create_item";
    private static final String CART_PAGE_LOCATION = "shop/cart";
    private static final String MANAGEMENT_PAGE_LOCATION = "shop/management";

    /**
     * @param authentication authentication principal
     * @return redirect to main page due to user authentication
     */
    @GetMapping
    public ModelAndView redirectByRole(final Authentication authentication) {
        if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
            return new ModelAndView(REDIRECT_TO + "/management");
        } else {
            return new ModelAndView(REDIRECT_TO + "/shopping");
        }
    }

    /**
     * @return ModelAndView of Shopping page
     */
    @GetMapping("/shopping")
    public ModelAndView openMainPage() {
        log.info("GET Main shop page");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TEMPLATE_LOCATION);
        modelAndView.addObject(PAGE_KEY, SHOPPING_PAGE_LOCATION);
        return modelAndView;
    }

    /**
     * @return ModelAndView of Create iten page
     */
    @GetMapping("/create-item")
    public ModelAndView openCreateItemPage() {
        log.info("GET Create item page");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TEMPLATE_LOCATION);
        modelAndView.addObject(PAGE_KEY, CREATE_ITEM_PAGE_LOCATION);
        return modelAndView;
    }

    /**
     * @return ModelAndView of Management page
     */
    @GetMapping("/management")
    public ModelAndView openManagementPage() {
        log.info("GET Management page");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TEMPLATE_LOCATION);
        modelAndView.addObject(PAGE_KEY, MANAGEMENT_PAGE_LOCATION);
        return modelAndView;
    }

    /**
     * @return ModelAndView of Cart page
     */
    @GetMapping("/cart")
    public ModelAndView openCartPage() {
        log.info("GET Cart page");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TEMPLATE_LOCATION);
        modelAndView.addObject(PAGE_KEY, CART_PAGE_LOCATION);
        return modelAndView;
    }
}
