# AntiFreecam evolved 1.2.3+mc26.1.2

- Add collision enforcement for Yannis' FreeCam during flight, tripod recalls, and in F6 follow mode.
- Prevent older FreeCam releases from hiding obstructions while collision enforcement is active.
- Bound collision checks for long camera jumps to 512 blocks per request.

# AntiFreecam evolved 1.2.2+mc26.1.2

- Require Fabric Loader 0.19.5 for current YACL/MixinExtras compatibility.
- Add optional Camera Tweaks integration (1.11.2-26.1 API).
- Enforce collisions during camera movement, activation, and saved-camera loading.
- Prevent saved-camera interpolation from bypassing collision checks.
- Retain this branch's existing server collision policy; no render-distance option is added.
