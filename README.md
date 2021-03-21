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
    - **当たったら消えるかどうか(モートン周りで)**
  - Player
- Scene
  - InfoSceneとGameSceneの管理
  - Scene間のインターフェース
- InfoScene
  - 画面に描画する
    - 描画以外の表示
    - ゲームスコアなどの表示
    - GameSceneの描画
- GameScene
  - GameObjectの更新を行う
  - Gameの描画を行う
  - 一つのシーンのみ更新する(描画が重いので)

- モートン分割
[モートン分割](https://qiita.com/bearjiro/items/1078db2d78f92898b813)
  - 分割数は8
  - オブジェクト位置の更新
  - モートン空間を計算しルート(1)、親(4)、子(16)、孫(64)に振り分ける
  - 孫からルートに向けてヒットチェックをする
  - 消せるオブジェクトならその都度消し、ヒットチェックが終わったオブジェクトはリストから取り除く(被オブジェクトはそのまま)
- Update->Draw->Collision