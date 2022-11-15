# windows 11
FROM ubuntu:22.04

ARG USER_NAME
ARG USER_GROUP_NAME
ARG USER_UID
ARG USER_GID

# general packages
ARG PKG="git vim curl unzip zip sudo tzdata"

SHELL ["/bin/bash", "-c"]

# setup timezone https://dev.to/grigorkh/fix-tzdata-hangs-during-docker-image-build-4o9m
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo TZ > /etc/timezone

RUN apt-get update \
    && apt-get install -y ${PKG} \
    && groupadd --gid ${USER_GID} ${USER_GROUP_NAME} \
    && useradd --uid ${USER_UID} --shell /bin/bash --gid ${USER_GID} -m ${USER_NAME} \
    && echo %${USER_GROUP_NAME} ALL=\(ALL\) NOPASSWD:ALL > /etc/sudoers.d/${USER_GROUP_NAME} \
    && chmod 0440 /etc/sudoers.d/${USER_GROUP_NAME}

# enable git
# https://github.com/devcontainers/cli/issues/98
# change to main branch https://dev-yakuza.posstree.com/git/default-branch/
RUN su ${USER_NAME} --command \
    'sudo git config --system --add safe.directory "/home/${USER_NAME}/workspace"'

# ---------------------------------- sdkman ----------------------------------
# https://sdkman.io/install
ARG JAVA_VERSION
ARG GRADLE_VERSION

RUN su ${USER_NAME} --command \
    'curl -s "https://get.sdkman.io" | bash \
    && source "${HOME}/.sdkman/bin/sdkman-init.sh" \
    && sdk install java "${JAVA_VERSION}" \
    && sdk install gradle ${GRADLE_VERSION}'
