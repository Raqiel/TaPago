---
name: tapago-escopo-e-custo
description: "TaPago é ficha de academia por enquanto, vai crescer depois, e o backend tem que ser barato/gratuito"
metadata: 
  node_type: memory
  type: project
  originSessionId: e125d66b-5265-4ff6-96be-87592fbb0042
  modified: 2026-10-08T17:29:45.579Z
---

O TaPago é, por enquanto, só uma ficha de academia; a intenção declarada (2026-08-02) é
adicionar outras funcionalidades depois. Poucos usuários esperados no curto prazo.

Restrição central: **não gastar dinheiro com backend**, mesmo se o app crescer.

Decisões tomadas em 2026-08-02:
- Firebase Auth com email/senha + Google (Credential Manager). Telefone/SMS descartado porque é cobrado sempre.
- Cloud Firestore como fonte da verdade dos treinos, com cache local ligado.
- Cloud Storage para as fotos — aceitou o plano Blaze sabendo que exige cartão cadastrado.

**Why:** ela escolheu explicitamente "dados + fotos" mesmo depois de eu avisar que Storage
obriga Blaze, então não vale mais sugerir tirar as fotos da nuvem.

Em 2026-10-08 ela decidiu **adiar o Firebase**. O código de Auth/Firestore/Storage ficou
escrito e compilando, mas desligado: plugin google-services removido do Gradle,
firebaseModule fora do Koin, splash indo direto pra Home, e o PhotoRepository apontando
pra LocalPhotoRepository (salva em filesDir/photos). Os arquivos parados estão em
data/remote/ e ui/screens/login/.

**How to apply:** ao propor qualquer serviço novo, dizer antes se exige cartão e qual a
franquia gratuita. Preferir sempre o que roda no Spark. Quando ela quiser religar o
Firebase, é só reverter esses 5 pontos, não reescrever nada. Ver [[tapago-idioma-pt-br]].
