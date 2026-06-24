# ──────────────────────────────────────────────
# Stage 1: Build the Liferay modules
# ──────────────────────────────────────────────
FROM eclipse-temurin:17-jdk AS builder

ARG GRADLE_VERSION=8.9
RUN apt-get update && apt-get install -y wget unzip && \
    wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -O /tmp/gradle.zip && \
    unzip -q /tmp/gradle.zip -d /opt && \
    rm /tmp/gradle.zip && \
    ln -s /opt/gradle-${GRADLE_VERSION}/bin/gradle /usr/local/bin/gradle

WORKDIR /workspace

COPY gradle.properties .
COPY build.gradle .
COPY settings.gradle .
COPY .blade.properties .
COPY gradle/ gradle/
COPY modules/ modules/

RUN gradle \
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

# Copy portal config
COPY configs/common/portal-setup-wizard.properties /mnt/liferay/files/portal-setup-wizard.properties
COPY configs/docker/portal-ext.properties          /mnt/liferay/files/portal-ext.properties

# Copy built JARs and fix ownership so the liferay user can deploy them
COPY --from=builder --chown=liferay:liferay /workspace/modules/todo/todo-api/build/libs/*.jar     /mnt/liferay/deploy/
COPY --from=builder --chown=liferay:liferay /workspace/modules/todo/todo-service/build/libs/*.jar /mnt/liferay/deploy/
COPY --from=builder --chown=liferay:liferay /workspace/modules/hello-world/build/libs/*.jar       /mnt/liferay/deploy/

EXPOSE 8080 11311