# 解决 IDE 中 JUnit5 导入错误

## 问题描述
IDE 显示错误：`The import org.junit cannot be resolved`

## 解决方案

### 方法 1：刷新项目（推荐）

#### IntelliJ IDEA
1. **右键点击项目根目录** → `Open Module Settings` (或按 `F4`)
2. 在 `Project Structure` 窗口中：
   - 选择 `Modules`
   - 选中你的模块
   - 点击 `Dependencies` 标签
   - 确认 `junit-platform-console-standalone-1.10.0.jar` 在列表中
   - 如果没有，点击 `+` → `JARs or directories` → 选择 `lib/junit-platform-console-standalone-1.10.0.jar`
3. 点击 `Apply` 和 `OK`
4. **刷新项目**：`File` → `Invalidate Caches / Restart` → `Invalidate and Restart`

#### VS Code with Java Extension Pack
1. 按 `Ctrl+Shift+P` 打开命令面板
2. 输入并选择：`Java: Clean Java Language Server Workspace`
3. 或者右键点击 `lib` 文件夹 → `Refresh`
4. 等待 Java 语言服务器重新索引项目

### 方法 2：手动添加库依赖

#### 在 IntelliJ IDEA 中
1. 打开 `File` → `Project Structure` (或按 `Ctrl+Alt+Shift+S`)
2. 选择 `Libraries` → 点击 `+` → `Java`
3. 浏览到：`d:\Sch_Exp\2026Spring\AircraftWar-base1.0\lib\junit-platform-console-standalone-1.10.0.jar`
4. 选择模块：勾选你的模块
5. 点击 `OK`

#### 在 VS Code 中
1. 打开 `.vscode/settings.json` (如果没有就创建)
2. 添加以下配置：
```json
{
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```

### 方法 3：重新加载项目

#### IntelliJ IDEA
1. 关闭项目
2. 重新打开项目
3. 等待索引完成

#### VS Code
1. 按 `Ctrl+Shift+P`
2. 输入：`Developer: Reload Window`
3. 等待 Java 扩展激活

### 方法 4：检查 .iml 文件

确认 `AircraftWar-base.iml` 文件包含以下配置：

```xml
<orderEntry type="module-library" scope="TEST">
  <library>
    <CLASSES>
      <root url="jar://$MODULE_DIR$/lib/junit-platform-console-standalone-1.10.0.jar!/" />
    </CLASSES>
    <JAVADOC />
    <SOURCES />
  </library>
</orderEntry>
```

✅ 当前项目已经包含此配置。

### 方法 5：验证 JAR 文件

确认 JUnit5 JAR 文件存在且完整：

```bash
# 在 PowerShell 中运行
Test-Path "d:\Sch_Exp\2026Spring\AircraftWar-base1.0\lib\junit-platform-console-standalone-1.10.0.jar"
```

✅ 文件已确认存在。

## 验证配置是否成功

1. 打开 `HeroAircraftTest.java`
2. 查看导入语句是否还有红色波浪线
3. 如果没有波浪线，说明配置成功
4. 尝试运行测试：
   - IntelliJ IDEA: 右键点击测试类 → `Run 'HeroAircraftTest'`
   - VS Code: 右键点击测试类 → `Run Test`

## 常见问题

### Q: 导入仍然显示错误
A: 尝试以下步骤：
1. 清理项目：`Build` → `Rebuild Project`
2. 删除 `.idea` 目录（除了 `.gitignore` 文件）
3. 重新打开项目

### Q: 测试可以编译但 IDE 仍显示错误
A: 这是 IDE 索引问题，不影响实际运行
1. 等待 IDE 完成索引
2. 或者重启 IDE

### Q: 运行时出现 ClassNotFoundException
A: 确保运行配置包含 JUnit5 JAR
- 运行配置 → VM options → 添加：`-cp "target/classes;lib/junit-platform-console-standalone-1.10.0.jar"`

## 命令行验证

如果 IDE 配置困难，可以直接使用命令行运行测试：

```bash
# 运行测试脚本
run-tests.bat

# 或手动运行
java -cp "target/classes;lib/junit-platform-console-standalone-1.10.0.jar" org.junit.platform.console.ConsoleLauncher --select-class edu.hitsz.aircraft.HeroAircraftTest
```

✅ 命令行测试已经通过，说明配置是正确的，只是 IDE 需要刷新。

## 推荐操作顺序

1. **首先尝试**：刷新项目（方法 1）
2. **如果不行**：清理缓存并重启 IDE
3. **仍然不行**：使用命令行运行测试（功能完全正常）

## 注意事项

- JUnit5 的 standalone JAR 已经包含所有必要依赖
- 不需要额外下载其他 JUnit JAR 文件
- 测试代码编译和运行都正常，只是 IDE 显示问题
- 如果 IDE 配置太麻烦，可以忽略显示错误，直接使用命令行测试
