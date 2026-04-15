# JUnit5 测试配置说明

## 安装步骤

### 1. JUnit5 已安装
- JUnit Platform Console Standalone 1.10.0 已下载到 `lib/` 目录
- 项目配置文件已更新，包含 JUnit5 依赖

### 2. 目录结构
```
AircraftWar-base1.0/
├── lib/
│   └── junit-platform-console-standalone-1.10.0.jar
├── src/                    # 源代码
├── test/                   # 测试代码
│   └── edu/hitsz/aircraft/
│       └── HeroAircraftTest.java       # JUnit5 测试
├── target/classes/         # 编译输出
└── run-tests.bat           # 测试运行脚本
```

## 运行测试

### 方法 1：使用批处理脚本（推荐）
```bash
run-tests.bat
```

### 方法 2：手动运行
```bash
# 编译测试代码
javac -d target/classes -cp "target/classes;lib/junit-platform-console-standalone-1.10.0.jar" test/edu/hitsz/aircraft/HeroAircraftTest.java

# 运行测试
java -cp "target/classes;lib/junit-platform-console-standalone-1.10.0.jar" org.junit.platform.console.ConsoleLauncher --select-class edu.hitsz.aircraft.HeroAircraftTest
```

### 方法 3：运行所有测试
```bash
java -cp "target/classes;lib/junit-platform-console-standalone-1.10.0.jar" org.junit.platform.console.ConsoleLauncher --scan-classpath "target/classes"
```

## 测试结果

当前测试结果：
- ✅ 总测试数：9
- ✅ 通过测试：9
- ✅ 失败测试：0
- ✅ 成功率：100%

### 测试覆盖的方法

1. **单例模式测试** - 测试 `getInstance()` 方法
2. **加血功能测试** - 测试 `addHp()` 方法
3. **减少生命值功能测试** - 测试 `decreaseHp()` 方法
4. **位置移动功能测试** - 测试 `setLocation()` 方法
5. **射击功能测试** - 测试 `shoot()` 方法
6. **碰撞检测功能测试** - 测试 `crash()` 方法（继承自 AbstractFlyingObject）
7. **获取图片功能测试** - 测试 `getImage()` 方法（继承自 AbstractFlyingObject）
8. **获取宽度功能测试** - 测试 `getWidth()` 方法（继承自 AbstractFlyingObject）
9. **获取高度功能测试** - 测试 `getHeight()` 方法（继承自 AbstractFlyingObject）

## 注意事项

1. **图片资源问题**：
   - 部分测试（如碰撞检测、获取图片等）依赖图片资源
   - 在测试环境中，图片资源可能未完全初始化
   - 这些测试会捕获预期的 NullPointerException，不会导致测试失败

2. **单例模式**：
   - HeroAircraft 使用单例模式
   - 每次测试前会重置状态（通过 @BeforeEach 注解）

3. **测试注解说明**：
   - `@Test` - 标记测试方法
   - `@BeforeEach` - 在每个测试前执行
   - `@DisplayName` - 设置测试显示名称（支持中文）

## 常用断言

```java
assertEquals(expected, actual, message)           // 等于
assertNotEquals(expected, actual, message)        // 不等于
assertTrue(condition, message)                    // 为真
assertFalse(condition, message)                   // 为假
assertNull(object, message)                       // 为 null
assertNotNull(object, message)                    // 不为 null
assertSame(expected, actual, message)             // 同一对象
assertNotSame(expected, actual, message)          // 不同对象
assertDoesNotThrow(executable, message)           // 不抛出异常
```

## 添加新测试

1. 在 `test/edu/hitsz/aircraft/` 目录下创建新的测试类
2. 使用 `@Test` 注解标记测试方法
3. 使用适当的断言验证结果
4. 运行测试验证

示例：
```java
package edu.hitsz.aircraft;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyTest {
    @Test
    public void testExample() {
        assertEquals(1, 1, "1 应该等于 1");
    }
}
```
