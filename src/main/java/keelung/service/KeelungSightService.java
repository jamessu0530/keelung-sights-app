package keelung.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import keelung.model.Sight;

@Service
public class KeelungSightService {
    private static final String BASE_URL = "https://www.travelking.com.tw";

    public List<Sight> getSightsByZone(String zone) throws IOException {
        if (zone == null || zone.isBlank()) {
            return List.of(); // 回傳空 List，讓 Controller 處理
        }

        String startUrl = BASE_URL + "/tourguide/taiwan/keelungcity/";
        Document doc = Jsoup.connect(startUrl).timeout(180000).get();
        Elements links = doc.select("a[href]");

        String zoneUrl = null;
        String zoneText = zone.trim();

        for (Element link : links) {
            String text = link.text().trim();
            String href = link.attr("href");
            if (text.equals(zoneText + "區")) {
                zoneUrl = href.startsWith("http") ? href : BASE_URL + href;
                break;
            }
        }

        if (zoneUrl == null) {
            return List.of(); // 找不到區域，回傳空 List
        }

        Document zoneDoc = Jsoup.connect(zoneUrl).timeout(180000).get();
        Element guidePoint = zoneDoc.getElementById("guide-point");
        if (guidePoint == null) {
            return List.of();
        }
        Element box = guidePoint.selectFirst(".box");
        if (box == null) {
            return List.of();
        }

        Elements sightLinks = box.select("a[href]");
        List<Sight> sights = new ArrayList<>();
        for (Element link : sightLinks) {
            String href = link.attr("href");
            String fullUrl = href.startsWith("http") ? href : BASE_URL + href;
            sights.add(getItem(fullUrl, zoneText));
        }
        return sights;
    }

    private Sight getItem(String url, String zone) throws IOException {
        Document doc = Jsoup.connect(url).timeout(180000).get();
        Sight sight = new Sight();
        sight.setZone(zone);
        // 安全地取得 sightName，避免 NullPointerException
        Element nameEl = doc.selectFirst("meta[itemprop=name]");
        sight.setSightName(nameEl != null ? nameEl.attr("content") : "");
        Element categoryEl = doc.selectFirst("span[property='rdfs:label'] strong");
        sight.setCategory(categoryEl != null ? categoryEl.text() : "");
        Element imageEl = doc.selectFirst("meta[itemprop=image]");
        sight.setPhotoURL(imageEl != null ? imageEl.attr("content") : "");
        Element descEl = doc.selectFirst("meta[itemprop=description]");
        sight.setDescription(descEl != null ? descEl.attr("content") : "");
        Element addrEl = doc.selectFirst("meta[itemprop=address]");
        sight.setAddress(addrEl != null ? addrEl.attr("content") : "");
        return sight;
    }
}


