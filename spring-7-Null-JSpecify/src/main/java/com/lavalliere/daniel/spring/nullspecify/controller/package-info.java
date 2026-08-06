// NOTE: the following could be skipped if instead put in the pom.xml file BUT with the current version of spring framework/boot
//       the dependencies : Erro prone Library : 2.50.0 is updated for Spring Boot 4 but NullAway : 0.13.8 is NOT

@NullMarked  // Everything in this package is non-null by default
package com.lavalliere.daniel.spring.nullspecify.controller;
import org.jspecify.annotations.NullMarked;