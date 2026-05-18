package com.finance.tracker.controller;

import com.finance.tracker.dao.SubscriptionDao;
import com.finance.tracker.dao.UserDao;
import com.finance.tracker.model.Subscription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionRestController {

    private final SubscriptionDao subscriptionDao;
    private final UserDao userDao;
    private final com.finance.tracker.service.ScheduledTasks scheduledTasks;

    public SubscriptionRestController(SubscriptionDao subscriptionDao, UserDao userDao, com.finance.tracker.service.ScheduledTasks scheduledTasks) {
        this.subscriptionDao = subscriptionDao;
        this.userDao = userDao;
        this.scheduledTasks = scheduledTasks;
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions(Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        return ResponseEntity.ok(subscriptionDao.findAll(userId));
    }

    @PostMapping
    public ResponseEntity<String> addSubscription(@RequestBody Subscription subscription, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        subscription.setUserId(userId);
        subscriptionDao.save(subscription);
        
        // Immediately process it if it's due today
        scheduledTasks.processDueSubscriptions();
        
        return ResponseEntity.ok("Subscription created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable int id, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        subscriptionDao.delete(id, userId);
        return ResponseEntity.ok("Subscription deleted");
    }
}
