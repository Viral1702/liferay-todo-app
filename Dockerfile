# Any instructions here will be appended to the end of the Dockerfile created by `createDockerfile`.# ──────────────────────────────────────────────
# Stage 1: Build the Liferay modules
# ──────────────────────────────────────────────
FROM eclipse-temurin:11-jdk AS builder

WORKDIR /workspace

# Copy Gradle wrapper and config first (layer cache)
COPY gradlew .
COPY gradle/ gradle/
COPY gradle.properties .
COPY build.gradle .
COPY settings.gradle* .
COPY .blade.properties .

# Copy all modules source
COPY modules/ modules/

# Make gradlew executable and build all module JARs
RUN chmod +x gradlew && \
    ./gradlew \
        :modules:todo:todo-api:jar \
        :modules:todo:todo-service:jar \
        :modules:hello-world:jar \
        --no-daemon \
        --stacktrace \
        -x test

# ──────────────────────────────────────────────
# Stage 2: Liferay runtime image
# ──────────────────────────────────────────────
FROM liferay/portal:7.4.3.132-ga132

# Copy portal config for docker environment
COPY configs/common/portal-setup-wizard.properties /mnt/liferay/files/portal-setup-wizard.properties
COPY configs/docker/portal-ext.properties          /mnt/liferay/files/portal-ext.properties

# Copy built JARs into Liferay's hot-deploy folder
COPY --from=builder /workspace/modules/todo/todo-api/build/libs/*.jar     /mnt/liferay/deploy/
COPY --from=builder /workspace/modules/todo/todo-service/build/libs/*.jar /mnt/liferay/deploy/
COPY --from=builder /workspace/modules/hello-world/build/libs/*.jar       /mnt/liferay/deploy/

# Expose Liferay HTTP and Gogo Shell ports
EXPOSE 8080 11311