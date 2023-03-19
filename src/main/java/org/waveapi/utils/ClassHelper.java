package org.waveapi.utils;

import javassist.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class ClassHelper {

    public static ClassLoader loader;

    static {
        try {
            loader = new URLClassLoader(new URL[]{new File("./waveAPI/classes").toURI().toURL()}, ClassHelper.class.getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public interface InterfaceImpl {
        String getMethods();
        String getInterface();
    }

    public interface Generator<M> {
        Class<M> getBaseClass();

        Class<?> getBaseMethods();

        List<InterfaceImpl> getInterfaces();
    }

    public static <T> Class<?> LoadOrGenerateCompoundClass(String name, Generator<T> generator, boolean generate) {
        if (generate) {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.makeClass(name);
            ctClass.defrost();
            try {
                ctClass.setSuperclass(pool.getCtClass(generator.getBaseClass().getName()));
                CtClass methods = pool.getCtClass(generator.getBaseMethods().getName());

                for (CtField field : methods.getDeclaredFields()) {
                    ctClass.addField(new CtField(field, ctClass));
                }

                for (CtConstructor constructor : methods.getDeclaredConstructors()) {
                    ctClass.addConstructor(new CtConstructor(constructor, ctClass, null));
                }

                for (CtMethod method : methods.getDeclaredMethods()) {
                    ctClass.addMethod(new CtMethod(method, ctClass, null));
                }

                for (InterfaceImpl impl : generator.getInterfaces()) {
                    ctClass.addInterface(pool.getCtClass(impl.getInterface()));
                    for (CtMethod method : pool.getCtClass(impl.getMethods()).getDeclaredMethods()) {
                        ctClass.addMethod(new CtMethod(method, ctClass, null));
                    }
                }

                ctClass.writeFile("./waveAPI/classes");
                ctClass.freeze();
            } catch (CannotCompileException | NotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return loader.loadClass(name);
        } catch (ClassNotFoundException | ClassCastException e) {
            return LoadOrGenerateCompoundClass(name, generator, true);
        }
    }

}
