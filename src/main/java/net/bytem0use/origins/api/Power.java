package net.bytem0use.origins.api;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class Power implements Comparable<Power>{
    private final Map<EntityAttribute, EntityAttributeModifier> attributeModifiers = Maps.<EntityAttribute, EntityAttributeModifier>newHashMap();
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final int INFINITE = -1;
    private StatusEffect type;
    private StatusEffectCategory category;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean showParticles;
    private boolean showIcon;
    private static final Power power = null;

    @Nullable
    private Power hiddenEffect;
    private Optional<FactorCalculationData> factorCalculationData;

    private final Supplier<FactorCalculationData> factorCalculationDataSupplier = () -> null;

    public Power(
            StatusEffect type,
            int duration,
            int amplifier,
            boolean ambient,
            boolean showParticles,
            boolean showIcon,
            @Nullable Power hiddenEffect,
            Optional<Power.FactorCalculationData> factorCalculationData
    ) {
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
        this.hiddenEffect = hiddenEffect;
        this.factorCalculationData = factorCalculationData;
    }

    public Power(StatusEffectCategory category, int duration, boolean ambient, boolean showParticles, boolean showIcon) {
        this.category = category;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
    }

    public Power(StatusEffectCategory category, int duration, boolean ambient, boolean showParticles, boolean showIcon, @Nullable Power hiddenEffect) {
        this.category = category;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
        this.hiddenEffect = hiddenEffect;
    }

    public Power(Power instance) {
        this.factorCalculationData = this.getFactorCalculationDataSupplier();
        this.copyFrom(instance);
    }

    public Optional<FactorCalculationData> getFactorCalculationData() {
        return this.factorCalculationData;
    }

    public Optional<FactorCalculationData> getFactorCalculationDataSupplier() {
        return Optional.ofNullable((FactorCalculationData)this.factorCalculationDataSupplier.get());
    }

    public Power addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.attributeModifiers.put(attribute, entityAttributeModifier);
        return this;
    }

    public Map<EntityAttribute, EntityAttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    void copyFrom(Power that) {
        this.duration = that.duration;
        this.amplifier = that.amplifier;
        this.ambient = that.ambient;
        this.showParticles = that.showParticles;
        this.showIcon = that.showIcon;
    }

    public boolean upgrade(Power that) {
        if (this.type != that.type) {
            LOGGER.warn("This method should only be called for matching effects!");
        }

        int i = this.duration;
        boolean bl = false;
        if (that.amplifier > this.amplifier) {
            if (that.lastsShorterThan(this)) {
                Power statusEffectInstance = this.hiddenEffect;
                this.hiddenEffect = new Power(this);
                this.hiddenEffect.hiddenEffect = statusEffectInstance;
            }

            this.amplifier = that.amplifier;
            this.duration = that.duration;
            bl = true;
        } else if (this.lastsShorterThan(that)) {
            if (that.amplifier == this.amplifier) {
                this.duration = that.duration;
                bl = true;
            } else if (this.hiddenEffect == null) {
                this.hiddenEffect = new Power(that);
            } else {
                this.hiddenEffect.upgrade(that);
            }
        }

        if (!that.ambient && this.ambient || bl) {
            this.ambient = that.ambient;
            bl = true;
        }

        if (that.showParticles != this.showParticles) {
            this.showParticles = that.showParticles;
            bl = true;
        }

        if (that.showIcon != this.showIcon) {
            this.showIcon = that.showIcon;
            bl = true;
        }

        return bl;
    }

    private boolean lastsShorterThan(Power effect) {
        return !this.isInfinite() && (this.duration < effect.duration || effect.isInfinite());
    }

    public boolean isInfinite() {
        return this.duration == -1;
    }

    public boolean isDurationBelow(int duration) {
        return !this.isInfinite() && this.duration <= duration;
    }

    public int mapDuration(Int2IntFunction mapper) {
        return !this.isInfinite() && this.duration != 0 ? mapper.applyAsInt(this.duration) : this.duration;
    }

    public StatusEffect getEffectType() {
        return this.type;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public boolean isAmbient() {
        return this.ambient;
    }

    public boolean shouldShowParticles() {
        return this.showParticles;
    }

    public boolean shouldShowIcon() {
        return this.showIcon;
    }

    public boolean update(LivingEntity entity, Runnable overwriteCallback) {
        if (this.isActive()) {
            int i = this.isInfinite() ? entity.age : this.duration;
            if (this.type.canApplyUpdateEffect(i, this.amplifier)) {
                this.applyUpdateEffect(entity);
            }

            this.updateDuration();
            if (this.duration == 0 && this.hiddenEffect != null) {
                this.copyFrom(this.hiddenEffect);
                this.hiddenEffect = this.hiddenEffect.hiddenEffect;
                overwriteCallback.run();
            }
        }

        this.factorCalculationData.ifPresent(factorCalculationData -> factorCalculationData.update(this));
        return this.isActive();
    }

    private boolean isActive() {
        return this.isInfinite() || this.duration > 0;
    }

    private int updateDuration() {
        if (this.hiddenEffect != null) {
            this.hiddenEffect.updateDuration();
        }

        return this.duration = this.mapDuration(duration -> duration - 1);
    }

    public void applyUpdateEffect(LivingEntity entity) {
        if (this.isActive()) {
            this.type.applyUpdateEffect(entity, this.amplifier);
        }
    }

    public String getTranslationKey() {
        return this.type.getTranslationKey();
    }

    public String toString() {
        String string;
        if (this.amplifier > 0) {
            string = this.getTranslationKey() + " x " + (this.amplifier + 1) + ", Duration: " + this.getDurationString();
        } else {
            string = this.getTranslationKey() + ", Duration: " + this.getDurationString();
        }

        if (!this.showParticles) {
            string = string + ", Particles: false";
        }

        if (!this.showIcon) {
            string = string + ", Show Icon: false";
        }

        return string;
    }

    private String getDurationString() {
        return this.isInfinite() ? "infinite" : Integer.toString(this.duration);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            return o instanceof Power statusEffectInstance && this.duration == statusEffectInstance.duration
                    && this.amplifier == statusEffectInstance.amplifier
                    && this.ambient == statusEffectInstance.ambient
                    && this.type.equals(statusEffectInstance.type);
        }
    }

    public int hashCode() {
        int i = this.type.hashCode();
        i = 31 * i + this.duration;
        i = 31 * i + this.amplifier;
        return 31 * i + (this.ambient ? 1 : 0);
    }

    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("Id", StatusEffect.getRawId(this.getEffectType()));
        this.writeTypelessNbt(nbt);
    }

    private void writeTypelessNbt(NbtCompound nbt) {
        nbt.putByte("Amplifier", (byte)this.getAmplifier());
        nbt.putInt("Duration", this.getDuration());
        nbt.putBoolean("Ambient", this.isAmbient());
        nbt.putBoolean("ShowParticles", this.shouldShowParticles());
        nbt.putBoolean("ShowIcon", this.shouldShowIcon());
        if (this.hiddenEffect != null) {
            NbtCompound nbtCompound = new NbtCompound();
            this.hiddenEffect.writeNbt(nbtCompound);
            nbt.put("HiddenEffect", nbtCompound);
        }

        this.factorCalculationData.flatMap(factorCalculationData -> FactorCalculationData.CODEC
                .encodeStart(NbtOps.INSTANCE, factorCalculationData)
                .resultOrPartial(LOGGER::error)).ifPresent(factorCalculationDataNbt -> nbt.put("FactorCalculationData", factorCalculationDataNbt));
    }

    @Nullable
    public static Power fromNbt(NbtCompound nbt) {
        int i = nbt.getInt("Id");
        StatusEffect statusEffect = StatusEffect.byRawId(i);
        return statusEffect == null ? null : fromNbt(statusEffect, nbt);
    }

    private static Power fromNbt(StatusEffect type, NbtCompound nbt) {
        int i = nbt.getByte("Amplifier");
        int j = nbt.getInt("Duration");
        boolean bl = nbt.getBoolean("Ambient");
        boolean bl2 = true;
        if (nbt.contains("ShowParticles", NbtElement.BYTE_TYPE)) {
            bl2 = nbt.getBoolean("ShowParticles");
        }

        boolean bl3 = bl2;
        if (nbt.contains("ShowIcon", NbtElement.BYTE_TYPE)) {
            bl3 = nbt.getBoolean("ShowIcon");
        }

        Power statusEffectInstance = null;
        if (nbt.contains("HiddenEffect", NbtElement.COMPOUND_TYPE)) {
            statusEffectInstance = fromNbt(type, nbt.getCompound("HiddenEffect"));
        }

        Optional<FactorCalculationData> optional;
        if (nbt.contains("FactorCalculationData", NbtElement.COMPOUND_TYPE)) {
            optional = FactorCalculationData.CODEC
                    .parse(new Dynamic<>(NbtOps.INSTANCE, nbt.getCompound("FactorCalculationData")))
                    .resultOrPartial(LOGGER::error);
        } else {
            optional = Optional.empty();
        }

        return new Power(type, j, Math.max(i, 0), bl, bl2, bl3, statusEffectInstance, optional);
    }

    public int compareTo(@NotNull Power statusEffectInstance) {
        int i = 32147;
        return (this.getDuration() <= 32147 || statusEffectInstance.getDuration() <= 32147) && (!this.isAmbient() || !statusEffectInstance.isAmbient())
                ? ComparisonChain.start()
                .compareFalseFirst(this.isAmbient(), statusEffectInstance.isAmbient())
                .compareFalseFirst(this.isInfinite(), statusEffectInstance.isInfinite())
                .compare(this.getDuration(), statusEffectInstance.getDuration())
                .compare(this.getEffectType().getColor(), statusEffectInstance.getEffectType().getColor())
                .result()
                : ComparisonChain.start()
                .compare(this.isAmbient(), statusEffectInstance.isAmbient())
                .compare(this.getEffectType().getColor(), statusEffectInstance.getEffectType().getColor())
                .result();
    }

    public static class FactorCalculationData {
        public static final Codec<FactorCalculationData> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Codecs.NONNEGATIVE_INT.fieldOf("padding_duration").forGetter(data -> data.paddingDuration),
                                Codec.FLOAT.fieldOf("factor_start").orElse(0.0F).forGetter(data -> data.factorStart),
                                Codec.FLOAT.fieldOf("factor_target").orElse(1.0F).forGetter(data -> data.factorTarget),
                                Codec.FLOAT.fieldOf("factor_current").orElse(0.0F).forGetter(data -> data.factorCurrent),
                                Codecs.NONNEGATIVE_INT.fieldOf("ticks_active").orElse(0).forGetter(data -> data.effectChangedTimestamp),
                                Codec.FLOAT.fieldOf("factor_previous_frame").orElse(0.0F).forGetter(data -> data.factorPreviousFrame),
                                Codec.BOOL.fieldOf("had_effect_last_tick").orElse(false).forGetter(data -> data.hadEffectLastTick)
                        )
                        .apply(instance, FactorCalculationData::new)
        );
        private final int paddingDuration;
        private float factorStart;
        private float factorTarget;
        private float factorCurrent;
        private int effectChangedTimestamp;
        private float factorPreviousFrame;
        private boolean hadEffectLastTick;

        public FactorCalculationData(
                int paddingDuration,
                float factorStart,
                float factorTarget,
                float factorCurrent,
                int effectChangedTimestamp,
                float factorPreviousFrame,
                boolean hadEffectLastTick
        ) {
            this.paddingDuration = paddingDuration;
            this.factorStart = factorStart;
            this.factorTarget = factorTarget;
            this.factorCurrent = factorCurrent;
            this.effectChangedTimestamp = effectChangedTimestamp;
            this.factorPreviousFrame = factorPreviousFrame;
            this.hadEffectLastTick = hadEffectLastTick;
        }

        public FactorCalculationData(int paddingDuration) {
            this(paddingDuration, 0.0F, 1.0F, 0.0F, 0, 0.0F, false);
        }

        public void update(Power effect) {
            this.factorPreviousFrame = this.factorCurrent;
            boolean bl = !effect.isDurationBelow(this.paddingDuration);
            this.effectChangedTimestamp++;
            if (this.hadEffectLastTick != bl) {
                this.hadEffectLastTick = bl;
                this.effectChangedTimestamp = 0;
                this.factorStart = this.factorCurrent;
                this.factorTarget = bl ? 1.0F : 0.0F;
            }

            float f = MathHelper.clamp((float)this.effectChangedTimestamp / this.paddingDuration, 0.0F, 1.0F);
            this.factorCurrent = MathHelper.lerp(f, this.factorStart, this.factorTarget);
        }

        public float lerp(LivingEntity entity, float tickDelta) {
            if (entity.isRemoved()) {
                this.factorPreviousFrame = this.factorCurrent;
            }

            return MathHelper.lerp(tickDelta, this.factorPreviousFrame, this.factorCurrent);
        }
    }


    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.attributeModifiers.entrySet()) {
            EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance((EntityAttribute)entry.getKey());
            if (entityAttributeInstance != null) {
                entityAttributeInstance.removeModifier((EntityAttributeModifier)entry.getValue());
            }
        }
    }

    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.attributeModifiers.entrySet()) {
            EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance((EntityAttribute)entry.getKey());
            if (entityAttributeInstance != null) {
                EntityAttributeModifier entityAttributeModifier = (EntityAttributeModifier)entry.getValue();
                entityAttributeInstance.removeModifier(entityAttributeModifier);
                entityAttributeInstance.addPersistentModifier(
                        new EntityAttributeModifier(
                                entityAttributeModifier.getId(),
                                this.getTranslationKey() + " " + amplifier,
                                this.adjustModifierAmount(amplifier, entityAttributeModifier),
                                entityAttributeModifier.getOperation()
                        )
                );
            }
        }
    }

    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier) {
        return modifier.getValue() * (amplifier + 1);
    }

    public boolean isBeneficial() {
        return this.category == StatusEffectCategory.BENEFICIAL;
    }
}
