package br.pucpr.mage;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collection;

import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Classe utilizada para a construção de novas malhas. Contém uma série de métodos para definição de atributos,
 * uniformes, index buffer e shader. Contém também versões do métodos com suporte a coleções, tipos da JOGL e outras
 * facilidades.
 */
public class MeshBuilder {
    private Mesh mesh;

    public MeshBuilder() {
        mesh = new Mesh();
        glBindVertexArray(mesh.getId());
    }

    //Buffers de atributos
    //--------------------
    public MeshBuilder addBufferAttribute(String name, ArrayBuffer data) {
        mesh.addAttribute(name, data);
        return this;
    }

    public MeshBuilder addBufferAttribute(String name, int elementSize, FloatBuffer values) {
        return addBufferAttribute(name, new ArrayBuffer(elementSize, values));
    }

    public MeshBuilder addFloatArrayAttribute(String name, int elementSize, float... values) {
        return addBufferAttribute(name, new ArrayBuffer(elementSize, values));
    }

    // Atributos do tipo Vector2
    // -------------------------
    public MeshBuilder addVector2fAttribute(String name, Collection<Vector2f> values) {
        FloatBuffer valueBuffer = BufferUtils.createFloatBuffer(values.size() * 2);
        for (Vector2f value : values) {
            valueBuffer.put(value.x).put(value.y);
        }
        valueBuffer.flip();
        return addBufferAttribute(name, 2, valueBuffer);
    }

    public MeshBuilder addVector2fAttribute(String name, Vector2f... values) {
        return addVector2fAttribute(name, Arrays.asList(values));
    }

    public MeshBuilder addVector2fAttribute(String name, float... values) {
        return addFloatArrayAttribute(name, 2, values);
    }

    // Atributos do tipo Vector3
    // -------------------------
    public MeshBuilder addVector3fAttribute(String name, Collection<Vector3f> values) {
        FloatBuffer valueBuffer = BufferUtils.createFloatBuffer(values.size() * 3);
        for (Vector3f value : values) {
            valueBuffer.put(value.x).put(value.y).put(value.z);
        }
        valueBuffer.flip();
        return addBufferAttribute(name, 3, valueBuffer);
    }

    public MeshBuilder addVector3fAttribute(String name, Vector3f... values) {
        return addVector3fAttribute(name, Arrays.asList(values));

    }

    public MeshBuilder addVector3fAttribute(String name, float... values) {
        return addFloatArrayAttribute(name, 3, values);
    }

    //Atributos do tipo Vector4
    //-------------------------
    public MeshBuilder addVector4fAttribute(String name, Collection<Vector4f> values) {
        FloatBuffer valueBuffer = BufferUtils.createFloatBuffer(values.size() * 4);
        for (Vector4f value : values) {
            valueBuffer.put(value.x).put(value.y).put(value.z).put(value.w);
        }
        valueBuffer.flip();
        return addBufferAttribute(name, 4, valueBuffer);
    }

    public MeshBuilder addVector4fAttribute(String name, Vector4f... values) {
        return addVector4fAttribute(name, Arrays.asList(values));
    }

    public MeshBuilder addVector4fAttribute(String name, float... values) {
        return addFloatArrayAttribute(name, 4, values);
    }

    // Index buffer
    // ------------
    public MeshBuilder setIndexBuffer(IndexBuffer indexBuffer) {
        mesh.setIndexBuffer(indexBuffer);
        return this;
    }

    public MeshBuilder setIndexBuffer(IntBuffer data) {
        return setIndexBuffer(new IndexBuffer(data));
    }

    public MeshBuilder setIndexBuffer(Collection<Integer> data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.size());
        for (int value : data) {
            buffer.put(value);
        }
        buffer.flip();
        return setIndexBuffer(buffer);
    }

    public MeshBuilder setIndexBuffer(int... data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data).flip();
        return setIndexBuffer(buffer);
    }

    // Shader
    // ------
    public MeshBuilder setShader(Shader shader) {
        mesh.setShader(shader);
        return this;
    }

    public MeshBuilder loadShader(String... shaders) {
        mesh.setShader(Shader.loadProgram(shaders));
        return this;
    }

    /**
     * Cria a malha previamente definida.
     * @return A malha criada.
     */
    public Mesh create() {
        glBindVertexArray(0);
        return mesh;
    }
}
