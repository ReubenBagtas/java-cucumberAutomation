package com.upkeep.automation.services.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.By;

@Getter
@AllArgsConstructor
public enum NavbarElements {

    WORK_ORDERS(By.xpath("//a[contains(@href,('/web/work-orders'))]"), ""),
    REPORTS(By.xpath("//a[contains(@href,('#/app/reporting/leaderboard'))]"), ""),
    REQUESTS(By.xpath("//a[contains(@href,('#/app/requests'))]"), ""),
    SHARED_WORK_ORDERS(By.xpath("//a[contains(@href,('#/app/shared'))]"), ""),
    //from here to XX, a few duplicates based on business type
    PROPERTIES(By.xpath("//a[contains(@href,('#/app/location'))]"), ""),
    LOCATIONS(By.xpath("//a[contains(@href,('#/app/location'))]"), ""),
    UNITS(By.xpath("//a[contains(@href,('#/app/assets'))]"), ""),
    EQUIPMENT(By.xpath("//a[contains(@href,('#/app/assets'))]"), ""),
    PARTS_INVENTORY(By.xpath("//a[contains(@href,('#/app/inventory'))]"), ""),
    PURCHASE_ORDERS(By.xpath("//a[contains(@href,('#/app/purchase-orders'))]"), ""),
    METERS(By.xpath("//a[contains(@href,('#/app/meters'))]"), ""),
    PEOPLE_AND_TEAMS(By.xpath("//a[contains(@href,('/web/people'))]"), ""),
    VENDORS_AND_CUSTOMERS(By.xpath("//a[contains(@href,('/web/vendors'))]"), ""),
    CATEGORIES(By.xpath("//a[contains(@href,('#/app/account/maintenance-categories'))]"), ""),
    FILES(By.xpath("//a[contains(@href,('#/app/account/files'))]"), ""),
    REQUEST_PORTAL(By.xpath("//a[contains(@href,('#/app/account/public-requests'))]"), ""),
    SETTINGS(By.xpath("//a[contains(@href,('/web/settings'))]"), "");

  private By selector;
  private String query;
}
