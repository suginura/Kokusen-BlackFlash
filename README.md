# Kokusen - Black Flash (JP/EN)

---

## 日本語

練習として作ったFabric MODです。某漫画に登場する必殺の一撃を繰り出すことができます。

### 機能

攻撃するたびに確率判定が行われ、当たれば黒閃（クリティカル）が発動します。クリティカル時はダメージが倍率分乗算されます。

### コマンド

| コマンド | 説明 | 例 |
|---|---|---|
| `/kokusen per <0.0~100.0>` | 黒閃の発生確率を変更 | `/kokusen per 50` |
| `/kokusen dmgMultiplier <1.0~100.0>` | ダメージ倍率を変更 | `/kokusen dmgMultiplier 80` |

設定はゲームを閉じても保存されます。(`.minecraft/config/kokusen-blackflash.json`)

---

## English

A Fabric MOD I made as practice, that lets you perform a critical punch from a certain manga.

### Features

Every attack triggers a probability check. On success, a Black Flash (critical hit) activates and damage is multiplied by the configured multiplier.

### Commands

| Command | Description | Example |
|---|---|---|
| `/kokusen per <0.0~100.0>` | Set the Black Flash chance | `/kokusen per 50` |
| `/kokusen dmgMultiplier <1.0~100.0>` | Set the damage multiplier | `/kokusen dmgMultiplier 80` |

Settings are saved between sessions. (`.minecraft/config/kokusen-blackflash.json`)
