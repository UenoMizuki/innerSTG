# innerSTG

# todo
- キーコンフィグ
- タイトル画面
- ステージ選択ステージ
# 仕様
- 画面サイズ
  - 1080x720
- パディング
  - (7+4,7+30)
- ゲーム画面サイズ(黒白緑青赤)
  - 600x720
    - 550x660
      - 500x600
        - 450x540
          - 400x480
- スコア表示(仮)
  - 480x720
    - 480x660
      - 480x600
        - 480x540
          - 480x480
# Object
- GameObject
    - double x,y
    - int width,height
    - double col
    - String tag
    - int hp
    - int damage
    - hit(gameObject a);
  - Player
- Scene
  - 画面に描画する
    - 描画以外の表示
    - ゲームスコアなどの表示
    - GameSceneの描画
- GameScene
  - GameObjectの更新を行う
  - Gameの描画を行う