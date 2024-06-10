// vite.config.js
import { defineConfig, loadEnv } from "file:///C:/workspace/nebula/nebula-vue/node_modules/vite/dist/node/index.js";
import vue from "file:///C:/workspace/nebula/nebula-vue/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import AutoImport from "file:///C:/workspace/nebula/nebula-vue/node_modules/unplugin-auto-import/dist/vite.js";
import { LayuiVueResolver } from "file:///C:/workspace/nebula/nebula-vue/node_modules/unplugin-vue-components/dist/resolvers.js";
import Components from "file:///C:/workspace/nebula/nebula-vue/node_modules/unplugin-vue-components/dist/vite.js";
import path from "path";
var __vite_injected_original_dirname = "C:\\workspace\\nebula\\nebula-vue";
var getEnv = (mode, target) => {
  return loadEnv(mode, process.cwd())[target];
};
var vite_config_default = ({ mode }) => defineConfig({
  plugins: [
    vue(),
    // htmlPlugin,
    AutoImport({
      imports: ["vue", "vue-router"],
      resolvers: [LayuiVueResolver()]
    }),
    Components({
      resolvers: [LayuiVueResolver()]
    })
  ],
  server: {
    host: "0.0.0.0",
    port: getEnv(mode, "VITE_PORT"),
    open: true,
    proxy: {
      // 选项写法
      [getEnv(mode, "VITE_APP_BASE_API")]: {
        target: "http://localhost:8080",
        changeOrigin: true,
        rewrite: (path2) => path2.replace([getEnv(mode, "VITE_APP_BASE_API")], "")
      }
      // 或者使用字符串简写
      // '/api': 'http://localhost:8080'
    }
  },
  resolve: {
    alias: {
      "@": path.resolve(__vite_injected_original_dirname, "src")
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJDOlxcXFx3b3Jrc3BhY2VcXFxcbmVidWxhXFxcXG5lYnVsYS12dWVcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkM6XFxcXHdvcmtzcGFjZVxcXFxuZWJ1bGFcXFxcbmVidWxhLXZ1ZVxcXFx2aXRlLmNvbmZpZy5qc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vQzovd29ya3NwYWNlL25lYnVsYS9uZWJ1bGEtdnVlL3ZpdGUuY29uZmlnLmpzXCI7aW1wb3J0IHtkZWZpbmVDb25maWcsbG9hZEVudn0gZnJvbSAndml0ZSdcclxuaW1wb3J0IHZ1ZSBmcm9tICdAdml0ZWpzL3BsdWdpbi12dWUnXHJcbmltcG9ydCBBdXRvSW1wb3J0IGZyb20gXCJ1bnBsdWdpbi1hdXRvLWltcG9ydC92aXRlXCI7XHJcbmltcG9ydCB7TGF5dWlWdWVSZXNvbHZlcn0gZnJvbSBcInVucGx1Z2luLXZ1ZS1jb21wb25lbnRzL2Rpc3QvcmVzb2x2ZXJzLmpzXCI7XHJcbmltcG9ydCBDb21wb25lbnRzIGZyb20gXCJ1bnBsdWdpbi12dWUtY29tcG9uZW50cy9kaXN0L3ZpdGUuanNcIjtcclxuaW1wb3J0IHBhdGggZnJvbSBcInBhdGhcIjtcclxuXHJcblxyXG5jb25zdCBnZXRFbnYgPSAobW9kZSwgdGFyZ2V0KSA9PiB7XHJcbiAgICByZXR1cm4gbG9hZEVudihtb2RlLCBwcm9jZXNzLmN3ZCgpKVt0YXJnZXRdXHJcbn1cclxuXHJcbi8vIGh0dHBzOi8vdml0ZWpzLmRldi9jb25maWcvXHJcbmV4cG9ydCBkZWZhdWx0ICh7bW9kZX0pPT5cclxuICAgIGRlZmluZUNvbmZpZyh7XHJcbiAgICBwbHVnaW5zOiBbdnVlKCksXHJcbiAgICAgICAgLy8gaHRtbFBsdWdpbixcclxuICAgICAgICBBdXRvSW1wb3J0KHtcclxuICAgICAgICAgICAgaW1wb3J0czogW1widnVlXCIsIFwidnVlLXJvdXRlclwiXSxcclxuICAgICAgICAgICAgcmVzb2x2ZXJzOiBbTGF5dWlWdWVSZXNvbHZlcigpXSxcclxuXHJcbiAgICAgICAgfSksXHJcbiAgICAgICAgQ29tcG9uZW50cyh7XHJcbiAgICAgICAgICAgIHJlc29sdmVyczogW0xheXVpVnVlUmVzb2x2ZXIoKV0sXHJcbiAgICAgICAgfSksXHJcbiAgICBdLFxyXG4gICAgc2VydmVyOiB7XHJcbiAgICAgICAgaG9zdDogJzAuMC4wLjAnLFxyXG4gICAgICAgIHBvcnQ6IGdldEVudihtb2RlLCdWSVRFX1BPUlQnKSxcclxuICAgICAgICBvcGVuOiB0cnVlLFxyXG4gICAgICAgIHByb3h5OiB7XHJcbiAgICAgICAgICAgIC8vIFx1OTAwOVx1OTg3OVx1NTE5OVx1NkNENVxyXG4gICAgICAgICAgICBbZ2V0RW52KG1vZGUsXCJWSVRFX0FQUF9CQVNFX0FQSVwiKV06IHtcclxuICAgICAgICAgICAgICAgIHRhcmdldDogJ2h0dHA6Ly9sb2NhbGhvc3Q6ODA4MCcsXHJcbiAgICAgICAgICAgICAgICBjaGFuZ2VPcmlnaW46IHRydWUsXHJcbiAgICAgICAgICAgICAgICByZXdyaXRlOihwYXRoKT0+IHBhdGgucmVwbGFjZSggW2dldEVudihtb2RlLFwiVklURV9BUFBfQkFTRV9BUElcIildLCcnKVxyXG4gICAgICAgICAgICB9LFxyXG4gICAgICAgICAgICAvLyBcdTYyMTZcdTgwMDVcdTRGN0ZcdTc1MjhcdTVCNTdcdTdCMjZcdTRFMzJcdTdCODBcdTUxOTlcclxuICAgICAgICAgICAgLy8gJy9hcGknOiAnaHR0cDovL2xvY2FsaG9zdDo4MDgwJ1xyXG4gICAgICAgIH1cclxuICAgIH0sXHJcbiAgICByZXNvbHZlOiB7XHJcbiAgICAgICAgYWxpYXM6IHtcclxuICAgICAgICAgICAgJ0AnOiBwYXRoLnJlc29sdmUoX19kaXJuYW1lLCAnc3JjJylcclxuICAgICAgICB9XHJcbiAgICB9LFxyXG5cclxufSlcclxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUFvUixTQUFRLGNBQWEsZUFBYztBQUN2VCxPQUFPLFNBQVM7QUFDaEIsT0FBTyxnQkFBZ0I7QUFDdkIsU0FBUSx3QkFBdUI7QUFDL0IsT0FBTyxnQkFBZ0I7QUFDdkIsT0FBTyxVQUFVO0FBTGpCLElBQU0sbUNBQW1DO0FBUXpDLElBQU0sU0FBUyxDQUFDLE1BQU0sV0FBVztBQUM3QixTQUFPLFFBQVEsTUFBTSxRQUFRLElBQUksQ0FBQyxFQUFFLE1BQU07QUFDOUM7QUFHQSxJQUFPLHNCQUFRLENBQUMsRUFBQyxLQUFJLE1BQ2pCLGFBQWE7QUFBQSxFQUNiLFNBQVM7QUFBQSxJQUFDLElBQUk7QUFBQTtBQUFBLElBRVYsV0FBVztBQUFBLE1BQ1AsU0FBUyxDQUFDLE9BQU8sWUFBWTtBQUFBLE1BQzdCLFdBQVcsQ0FBQyxpQkFBaUIsQ0FBQztBQUFBLElBRWxDLENBQUM7QUFBQSxJQUNELFdBQVc7QUFBQSxNQUNQLFdBQVcsQ0FBQyxpQkFBaUIsQ0FBQztBQUFBLElBQ2xDLENBQUM7QUFBQSxFQUNMO0FBQUEsRUFDQSxRQUFRO0FBQUEsSUFDSixNQUFNO0FBQUEsSUFDTixNQUFNLE9BQU8sTUFBSyxXQUFXO0FBQUEsSUFDN0IsTUFBTTtBQUFBLElBQ04sT0FBTztBQUFBO0FBQUEsTUFFSCxDQUFDLE9BQU8sTUFBSyxtQkFBbUIsQ0FBQyxHQUFHO0FBQUEsUUFDaEMsUUFBUTtBQUFBLFFBQ1IsY0FBYztBQUFBLFFBQ2QsU0FBUSxDQUFDQSxVQUFRQSxNQUFLLFFBQVMsQ0FBQyxPQUFPLE1BQUssbUJBQW1CLENBQUMsR0FBRSxFQUFFO0FBQUEsTUFDeEU7QUFBQTtBQUFBO0FBQUEsSUFHSjtBQUFBLEVBQ0o7QUFBQSxFQUNBLFNBQVM7QUFBQSxJQUNMLE9BQU87QUFBQSxNQUNILEtBQUssS0FBSyxRQUFRLGtDQUFXLEtBQUs7QUFBQSxJQUN0QztBQUFBLEVBQ0o7QUFFSixDQUFDOyIsCiAgIm5hbWVzIjogWyJwYXRoIl0KfQo=
