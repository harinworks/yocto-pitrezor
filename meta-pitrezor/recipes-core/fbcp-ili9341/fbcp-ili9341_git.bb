SUMMARY = "fbcp-ili9341 application"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.txt;md5=e07269cd84249a454c5d152cf5176dd5"

DEPENDS = "userland"
RDEPENDS:${PN} = "userland"

SRC_URI = "git://github.com/zxfishhack/fbcp-ili9341.git;branch=rpi2w-64bit-port \
           file://start_mirrorhdmi \
          "

SRCREV = "0840eb3da2f514f0abaf09293292b8bbaf335943"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-DWAVESHARE_ST7789VW_HAT=ON \
                 -DSPI_BUS_CLOCK_DIVISOR=30 \
                 -DBACKLIGHT_CONTROL=ON \
                 -DSTATISTICS=0 \
                 -DUSE_DMA_TRANSFERS=OFF \
                 -DUSE_VCSM_CMA=OFF \
                "
EXTRA_OECMAKE:append:raspberrypi0-wifi = " -DARMV6Z=ON"
EXTRA_OECMAKE:append:raspberrypi4-64 = " -DAARCH64=ON -DARMV8A=ON"
EXTRA_OECMAKE:append:raspberrypi0-2w-64 = " -DAARCH64=ON -DARMV8A=ON"

do_configure:prepend() {
    echo 'target_link_libraries(fbcp-ili9341 vchostif)' >> "${S}/CMakeLists.txt"
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/start_mirrorhdmi ${D}${bindir}
    install -m 0755 ${B}/fbcp-ili9341 ${D}${bindir}
}
