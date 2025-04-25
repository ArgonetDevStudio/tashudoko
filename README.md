# 🚴‍♂️ TashuDoko – Your End-of-Day Bike Station Reminder

**TashuDoko** is a lightweight, daily Slack notification service that helps you wrap up your day with a healthy ride
home.  
Every weekday around the end of working hours, it sends a neatly formatted Slack message showing all *Tashu* bike
stations within walking distance from your designated location – sorted by distance and availability.

No more wandering around looking for an available bike. Just check Slack and grap. 🚲

---

## What It Does

At a configured time every day, TashuDoko:

1. Fetches live data from the official [Tashu Public API](https://bikeapp.tashu.or.kr)
2. Filters stations within a certain radius of your **base point**
3. Sends a Slack message listing available stations:
    - Station name and address
    - Distance (in meters)
    - Available bikes (with visual indicators)
    - [위치보기] button that opens **Naver Maps** with directions

Example Slack message:

```
가까운 이용 가능 타슈 목록 🚲 (2025-04-25 (금) 18:00)

🟠 1. 만년동 기업은행  
만년동 296 (74m)  
대여가능: `1`       [위치보기]

🟢 2. 만년동 크리스탈웨딩홀  
만년동 305 (76m)  
대여가능: `4`       [위치보기]
...
```

---

## Smart Visual Indicators

Each station is tagged with a color-coded icon based on bike availability:

| Emoji | Availability | Meaning          |
|-------|--------------|------------------|
| 🟢    | 3+           | Plenty available |
| 🟡    | 2            | Moderate         |
| 🟠    | 1            | Limited – hurry! |
| 🚫    | 0            | None – hidden 🚫 |

Stations with `0` bikes are automatically excluded to avoid clutter.

---

## 🛠️ Built With

- **Java 21**
- **Spring Boot 3**

---

## ⚙️ Configuration

Before running the service, you need to set the following properties:

```yaml
config:
  base-name: "Argonet"
  base-latitude: 36.368725
  base-longitude: 127.380790
  max-distance-meter: 400

tashu-api:
  url: "https://bikeapp.tashu.or.kr:50041/v1/openapi"
  key: "API_KEY_HERE"

slack:
  url: "SLACK_WEBHOOK_URL_HERE"
```

- `base-name`: A friendly name for your base location (used for logging and clarity)
- `base-latitude` and `base-longitude`: Coordinates of your base location. You can easily find them using Google Maps.
- `max-distance-meter`: Only stations within this distance will be shown
- `tashu-api.key`: Your Tashu API key to fetch bike data
- `slack.url`: Webhook URL where messages are posted

---

## 📄 License

MIT License.  
Feel free to fork and build your own bike-friendly Slack tools

---

