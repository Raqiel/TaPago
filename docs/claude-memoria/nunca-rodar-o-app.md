---
name: nunca-rodar-o-app
description: "Nunca rodar, instalar ou abrir o app em device/emulador — risco de cair no aparelho errado"
metadata:
  node_type: memory
  type: feedback
  originSessionId: e125d66b-5265-4ff6-96be-87592fbb0042
  modified: 2026-10-08T18:32:30.500Z
---

Nunca rodar o app, instalar APK, abrir no emulador nem usar adb pra iniciar/inspecionar
o aplicativo. Verificação se limita a compilar (`assembleDebug`) e ler o código.

**Why:** ela avisou em 2026-10-08 que eu posso acabar rodando no device errado — o adb
pode estar apontando pra um aparelho que não é o que ela quer mexer.

**How to apply:** parar na build. Nada de `adb install`, `adb shell am start`, screencap,
`run-as` ou logcat. Quando precisar confirmar comportamento visual, descrever o que o
código faz e pedir pra ela testar, em vez de testar sozinha. Ver [[tapago-escopo-e-custo]].
